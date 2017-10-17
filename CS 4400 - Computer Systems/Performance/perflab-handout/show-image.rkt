#lang racket
(require racket/draw)

(define pngs? #f)

(define (show-bitmap path)
  (define-values (w h nums)
    (call-with-input-file*
     path
     (lambda (i)
       (define w (read i))
       (define h (read i))
       
       (define nums
         (for/vector ([i (in-port read i)])
           i))
       
       (values w h nums))))
  
  (define bm (make-bitmap w h))
  (define dc (send bm make-dc)) 
  
  (define (px i j c)
    (define v (vector-ref nums (+ c (* (+ (* i w) j) 3))))
    (arithmetic-shift v -8))
  
  (for* ([i (in-range w)]
         [j (in-range h)])
    (send dc set-pixel j i (make-color (px i j 0) (px i j 1) (px i j 2))))
  
  (cond
   [pngs?
    (send bm save-file (path-replace-suffix path #".png") 'png)]
   [else
    (define (gui-dynamic-require s)
      (dynamic-require 'racket/gui/base s))
    (define f (new (gui-dynamic-require 'frame%)
                   [label path]))
    (new (gui-dynamic-require 'message%)
         [label bm]
         [parent f])
    
    (send f show #t)]))

(command-line
 #:once-each
 [("--png") "Write a .png variant of each <file>"
  (set! pngs? #t)]
 #:args
 file
 (for-each show-bitmap file))
