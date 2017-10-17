#lang racket/base
(require racket/cmdline
         net/url
         racket/port
         racket/place)

;; Number of entries will be the product of these:
(define NUM-PLACES 4)       ; base parallelism
(define NUM-USERS 10)       ; more concurrency
(define NUM-ITERATIONS 100) ; repetitions

(define CONTENT-LENGTH 4096) ; size of each entry

;; Intended to be less than NUM-ITERATIONS:
(define NUM-TOPICS 5)

;; Sending lots of headers sometimes will
;; make some connections sluggish, maybe worth
;; a try:
(define MAX-JUNK-HEADERS 0)

(define-values (host port)
  (command-line
   #:args (host port)
   (values host port)))

(module+ main
  (for-each place-wait
            (for/list ([p NUM-PLACES])
              (define pl (run-group))
              (place-channel-put pl p)
              pl)))
  
(define user-1 "%%one%%")
(define user-2 "\">two?")

(define (run-group)
  (place
   pch
   (define p (sync pch))
   
   (define (make-topic i)
     (format "...topic&~a/~a..." (modulo i NUM-TOPICS) p))

   (define root-url
     (string->url
      (format "http://~a:~a/" host port)))

   (define say-url (combine-url/relative root-url "say"))

   (define topic-entries (make-hash))

   (for ([i NUM-ITERATIONS])
     (define topic (make-topic i))
     (define entries (hash-ref! topic-entries topic make-hash))
     (for-each
      sync
      (for/list ([j NUM-USERS])
        (thread
         (lambda ()
           (define user (case j
                          [(0) user-1]
                          [(1) user-2]
                          [else (format "u~a" j)]))
           (define content (format "[~a,~a]~a" i j (make-string CONTENT-LENGTH #\*)))
           (hash-set! entries (format "~a: ~a" user content) 2) ; 2 because of "import" below
           (call/input-url (struct-copy url say-url
                                        [query (list
                                                (cons 'user user)
                                                (cons 'topic topic)
                                                (cons 'content
                                                      content))])
                           (lambda (url)
                             (get-pure-port url
                                            (for/list ([i (random (add1 MAX-JUNK-HEADERS))])
                                              (format "x-junk-~a: garbage" i))))
                           port->string)))))
     (let ([entries (hash-copy entries)])
       (call/input-url (struct-copy url (combine-url/relative root-url
                                                              "conversation")
                                    [query (list
                                            (cons 'topic topic))])
                       get-pure-port
                       (lambda (i)
                         (for ([l (in-lines i 'return-linefeed)])
                           (define c (hash-ref entries l #f))
                           (unless c
                             (raise-user-error 'stress "unexpected result line for ~s: ~s" topic l))
                           (cond
                            [(positive? c)
                             (hash-set! entries l 0)]
                            [else
                             (raise-user-error 'stress "extra copy of expected entry for ~s: ~s" topic l)]))))))
   
   (for ([topic (in-hash-keys topic-entries)])
     (void (call/input-url (struct-copy url (combine-url/relative root-url
                                                                  "import")
                                        [query (list
                                                (cons 'topic topic)
                                                (cons 'host host)
                                                (cons 'port port))])
                           get-pure-port
                           port->string)))

   (for ([topic (in-hash-keys topic-entries)])
     (define entries (hash-ref topic-entries topic))
     (call/input-url (struct-copy url (combine-url/relative root-url
                                                            "conversation")
                                  [query (list
                                          (cons 'topic topic))])
                     get-pure-port
                     (lambda (i)
                       (for ([l (in-lines i 'return-linefeed)])
                         (define c (hash-ref entries l #f))
                         (unless c
                           (raise-user-error 'stress "unexpected result line for ~s: ~s" topic l))
                         (cond
                          [(positive? c)
                           (hash-set! entries l (sub1 c))]
                          [else
                           (raise-user-error 'stress "extra copy of expected entry for ~s: ~s" topic l)]))))

     (for ([(k v) (in-hash entries)])
       (unless (zero? v)
         (raise-user-error 'stress "expected entry for ~s missing: ~s" topic k))))))

