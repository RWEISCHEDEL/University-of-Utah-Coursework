#lang racket/base
(require racket/cmdline
         net/url
         net/head
         racket/port)

(define-values (host port)
  (command-line
   #:args (host port)
   (values host port)))

(define topic1 "demo")
(define topic2 "alternate")

(define root-url
  (string->url
   (format "http://~a:~a/" host port)))

(define fail? #f)

;; Check that the root is an HTML page:
(define-values (p headers) (get-pure-port/headers root-url #:status? #t))
(close-input-port p)
(unless (regexp-match? #rx"^HTTP/1[.][01] 200" headers)
  (set! fail? #t)
  (eprintf "root doesn't appear to succeed\n"))
(unless (regexp-match? #rx"text/html" (extract-field "content-type" headers))
  (set! fail? #t)
  (eprintf "root doesn't have a \"text/html\" content type\n"))

(define (get-conversation topic)
  (call/input-url (struct-copy url (combine-url/relative root-url
                                                         "conversation")
                               [query (list
                                       (cons 'topic topic))])
                  get-pure-port
                  port->string))

;; Get initial conversation
(define orig-c1 (get-conversation topic1))
(define orig-c2 (get-conversation topic2))

;; Add to conversation
(void
 (call/input-url (struct-copy url (combine-url/relative root-url
                                                        "say")
                              [query (list
                                      (cons 'user "me")
                                      (cons 'topic topic1)
                                      (cons 'content "this is a test"))])
                 get-pure-port
                 port->string))

;; Check updated conversation
(define new-c1 (get-conversation topic1))
(define new-c2 (get-conversation topic2))

(define expected-c1 (string-append orig-c1
                                   "me: this is a test\r\n"))

(unless (equal? new-c1 expected-c1)
  (set! fail? #t)
  (eprintf (string-append "conversation didn't change in the expected way;\n"
                          "  expected: ~s\n"
                          "  got: ~s\n")
           expected-c1
           new-c1))
(unless (equal? new-c2 orig-c2)
  (set! fail? #t)
  (eprintf (string-append "conversation change unexpectedly;\n"
                          "  expected: ~s\n"
                          "  got: ~s\n")
           orig-c2
           new-c2))

;; Add to other conversation
(void
 (call/input-url (struct-copy url (combine-url/relative root-url
                                                        "say")
                              [query (list
                                      (cons 'user "someone else")
                                      (cons 'topic topic2)
                                      (cons 'content "this is only a test"))])
                 get-pure-port
                 port->string))

;; Check updated conversation
(define newer-c1 (get-conversation topic1))
(define newer-c2 (get-conversation topic2))

(define expected-c2 (string-append orig-c2
                                   "someone else: this is only a test\r\n"))

(unless (equal? newer-c2 expected-c2)
  (set! fail? #t)
  (eprintf (string-append "conversation didn't change in the expected way;\n"
                          "  expected: ~s\n"
                          "  got: ~s\n")
           expected-c2
           newer-c2))
(unless (equal? new-c1 newer-c1)
  (set! fail? #t)
  (eprintf (string-append "conversation change unexpectedly;\n"
                          "  expected: ~s\n"
                          "  got: ~s\n")
           new-c1
           newer-c1))

;; Conclusion
(if fail?
    (exit 1)
    (printf "Simple tests passed\n"))
