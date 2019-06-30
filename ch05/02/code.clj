(ns ch05.ex02)

; Implement the comp function
; n.b completed at page 106 where two-comp was given as an example of using
; comp - prior to reaching chapter exercises.

(defn two-comp
  "Compose two functions"
  [f g]
  (fn [& args]
    (f (apply g args))))

((two-comp inc *) 2 3)

; reimplementation 
(defn comp'
  "Compose functions. Re-implementation of Clojure's comp function.
   Matches original multiple-arity signature"
  ([] identity)
  ([f] f)
  ([f g]
   (fn [& args]
     (f (apply g args))))
  ([f g & fs]
   (fn [& args]
     (f (apply (apply comp' g fs) args)))))

((comp') 1) ; identity
; => 1
((comp' inc) 1)
; => 2
((comp' inc *) 2 3)
; => 7
((comp' inc inc *) 2 3)
; => 8
((comp' inc inc inc *) 2 3)
; => 9
((comp' inc inc inc inc *) 2 3)
; => 10