#lang racket/base
(require racket/tcp
         racket/cmdline
         racket/port)

(define-values (host port)
  (command-line
   #:args (host port)
   (values host port)))


(define (go sleep-amt)
  (define-values (i o) (tcp-connect host (string->number port)))
  (sleep sleep-amt)
  (fprintf o "GET /say?user=me&topic=stuff&content=hi HTTP/1.0\r\n\r\n")
  (flush-output o)
  (port->string i)
  (close-input-port i)
  (close-output-port o))

(define slow-t (thread (lambda () (go 5))))

(define sema (make-semaphore))

(define trouble-t
  (thread
   (lambda ()
     (for ([j 500])
       (define-values (i o) (tcp-connect host (string->number port)))
       (semaphore-post sema)
       (when (zero? (modulo j 10)) (fprintf o "garbage\r\n"))
       (close-input-port i)
       (close-output-port o)))))

;; encourage trouble:
(for ([i 5]) (sync sema))

(define now (current-seconds))
(void (go 0))

(unless ((- (current-seconds) now) . < . 2)
  (error 'stall "seems too long"))

(printf "Fast one done... waiting for slow one\n")
(thread-wait slow-t)

(printf "Making sure that trouble is done\n")
(thread-wait trouble-t)
