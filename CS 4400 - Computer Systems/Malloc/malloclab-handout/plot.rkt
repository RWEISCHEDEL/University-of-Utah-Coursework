#lang racket/base
(require racket/gui/base
         racket/class
         racket/cmdline)

(define trace-file
  (command-line
   #:args
   ([trace-file #f])
   trace-file))

(define ids (make-hasheq))

(define (read-sizes in)
  (for ([i 4]) (read-line in))
  (list->vector
   (let loop ([size 0])
     (define t (read in))
     (cons size
           (case t
             [(a)
              (define id (read in))
              (define amt (read in))
              (hash-set! ids id amt)
              (loop (+ size amt))]
             [(f)
              (define id (read in))
              (define amt (hash-ref ids id))
              (loop (- size amt))]
             [(r)
              (define id (read in))
              (define amt (read in))
              (define old-amt (hash-ref ids id))
              (hash-set! ids id amt)
              (loop (- (+ size amt) old-amt))]
             [else
              (unless (eof-object? t)
                (error 'plt "unexpected in trace: ~e" t))
              null])))))

(define sizes
  (if trace-file
      (call-with-input-file* trace-file read-sizes)
      (read-sizes (current-input-port))))

(define max-amt (for/fold ([v 0]) ([amt (in-vector sizes)])
                  (max v amt)))

(define f (new frame%
               [label "Plot"]
               [width 800]
               [height 600]))
(void
 (new canvas%
      [parent f]
      [paint-callback (lambda (c dc)
                        (send dc draw-text (format "~a" max-amt) 0 0)
                        (define-values (w h) (send c get-client-size))
                        (for ([amt (in-vector sizes)]
                              [i (in-naturals)])
                          (define x (* i (/ w (vector-length sizes))))
                          (send dc draw-line
                                x h
                                x (- h (* h (/ amt max-amt))))))]))

(send f show #t)
