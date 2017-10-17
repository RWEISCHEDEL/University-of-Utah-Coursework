#lang racket/base
(require racket/cmdline)

(define-values (out expected)
  (command-line
   #:args (out expected)
   (values out expected)))

(define (get-results i)
  (define-values (ht func)
    (for/fold ([ht #hash()] [func #f]) ([l (in-lines i)])
      (cond
       [(regexp-match? #rx"^[a-zA-Z0-9_]+$" l)
        (when (hash-ref ht l #f)
          (error 'diff "found function name a second time in ~s" l))
        (values ht l)]
       [(not func)
        (error 'diff "expected a function name with no leading or trailing spaces in ~s" l)]
       [(regexp-match? #rx"^  [(][a-zA-Z0-9_]+[)]$" l)
        (values (hash-update ht func (lambda (s) (hash-set s (substring l 2) #t)) #hash())
                func)]
       [(regexp-match? #rx"^  [a-zA-Z0-9_]+$" l)
        (values (hash-update ht func (lambda (s) (hash-set s (substring l 2) #t)) #hash())
                func)]
       [else
        (error 'diff
               (string-append "expected either a function name,"
                              " a variable name after two spaces,"
                              " or a called function name in parentheses after two spaces"
                              " in ~s")
               l)])))
  ht)

(unless (equal? (call-with-input-file* out get-results)
                (call-with-input-file* expected get-results))
  (error 'diff "~a and ~a differ" out expected))
