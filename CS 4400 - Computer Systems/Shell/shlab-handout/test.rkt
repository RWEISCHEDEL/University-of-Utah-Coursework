#lang racket/base
(require racket/cmdline
         racket/system
         racket/path
         racket/string)

(define whoosh "whoosh")

(define fail? #f)

(define script-file-or-dir
  (command-line
   #:once-each
   [("--program") whoosh-executable "Select a `whoosh` executable"
    (set! whoosh whoosh-executable)]
   #:args
   (script-or-dir)
   script-or-dir))

(define (get-actions f)
  (call-with-input-file*
   f
   (lambda (i)
     (cond
      [(regexp-try-match #rx"# Needed input:\n" i)
       (define lines (get-lines i null cons cons))
       (define steps
         (for/list ([in (in-list lines)])
           (cond
             [(equal? in "ctl-c")
              (lambda (p i)
                (unless (eq? 'running (p 'status))
                  (set! fail? #t)
                  (eprintf " FINISHED TOO EARLY\n"))
                (p 'interrupt))]
             [(regexp-match #rx"^sleep ([0-9]+(?:[.][0-9]*)?)$" in)
              => (lambda (m)
                   (lambda (p i)
                     (sleep (string->number (cadr m)))))]
             [else
              (error 'get-action "unknown input-step: ~s" in)])))
       (lambda (p i)
         (for ([step (in-list steps)])
           (step p i)))]
      [else (lambda (p i) (void))]))))

(define (get-matcher f)
  (call-with-input-file*
   f
   (lambda (i)
     (define (get-output-lines)
       (get-lines i
                  ""
                  (lambda (l m) (string-append l "\n" m))
                  (lambda (l m) (string-append l m))))
     (cond
      [(regexp-try-match #rx"# Expected output:\n" i)
       (define lines (get-output-lines))
       (cond
        [(regexp-try-match #rx"^# OR\n" i)
         (define alt-lines (get-output-lines))
         (lambda (s)
           (or (string=? s lines)
               (string=? s alt-lines)))]
        [else
         (lambda (s) (string=? s lines))])]
      [(regexp-try-match #rx"# Expect output matching:\n" i)
       (define rx (pregexp (string-trim (get-output-lines))))
       (lambda (s) (regexp-match? rx s))]
      [else (error 'get-matcher
                   "could not find expected output in script\n  script: ~a"
                   f)]))))

(define (get-minimum-seconds f)
  (call-with-input-file*
   f
   (lambda (i)
     (cond
      [(regexp-try-match #rx"# Minimum seconds:\n" i)
       (define n (get-lines i "" string-append string-append))
       (define secs (string->number n))
       (unless secs
         (error 'get-minimum-seconds "base seconds specification: ~s" n))
       secs]
      [else
       0]))))

(define (get-lines i base combine combine-continue)
  (let loop ()
    (cond
     [(regexp-try-match #rx"^#  [^\n]*\n" i)
      => (lambda (m)
           (define line (bytes->string/utf-8 
                         (subbytes (car m)
                                   3
                                   (sub1 (bytes-length (car m))))))
           (define more (loop))
           (if (regexp-match? #rx"\\\\$" line)
               (combine-continue (substring line 0 (sub1 (string-length line))) more)
               (combine line more)))]
     [else base])))

(for ([f (if (directory-exists? script-file-or-dir)
             (directory-list script-file-or-dir #:build? #t)
             (list script-file-or-dir))]
      #:when (path-has-extension? f #".whoosh"))
  (printf "Trying ~a\n" f)
  (define input-actions (get-actions f))
  (define output-matcher (get-matcher f))
  (define min-secs (get-minimum-seconds f))
  (define o (open-output-bytes))
  (define e (open-output-bytes))
  (define c (make-custodian))
  (define started (current-seconds))
  (define-values (_o in pid _e p)
    (apply values
           (parameterize ([current-subprocess-custodian-mode 'kill]
                          [current-custodian c])
             (process*/ports o #f e whoosh f))))
  (input-actions p in)
  (define th
    (parameterize ([current-custodian c])
      (thread (lambda () (p 'wait)))))
  (unless (sync/timeout 10 th)
    (set! fail? #t)
    (eprintf " TIMEOUT\n"))
  (custodian-shutdown-all c)
  (when ((- (current-seconds) started) . < . min-secs)
    (set! fail? #t)
    (eprintf " COMPLETED TOO QUICKLY %d\n"))
  (unless (equal? 0 (p 'exit-code))
    (eprintf " NON-ZERO EXIT CODE: ~s\n" (p 'status))
    (set! fail? #t))
  (cond
   [(output-matcher (get-output-string o))
    (printf " Passed output check\n")]
   [else
    (set! fail? #t)
    (eprintf " FAILED: ~s\n" (get-output-string o))])
  (unless (equal? "" (get-output-string e))
    (set! fail? #t)
    (eprintf " NON-EMPTY STDERR: ~s\n" (get-output-string e))))

(when fail?
  (exit 1))
