(ns ch08.ex02)

(defmacro or'
  "Same as built in or"
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (or' ~@next)))))

(or')
; => nil

(or' :foo)
; => :foo

(or' true false)
; => true

(or' true false false)
; => true

(or' false :foo)
; => :foo

(or' false 7)
; => 7 

(or' nil nil)
; => nil

(or' true (println "I shouldn't be evaluated"))
; => true

(or' false (println "I should be evaluated"))
; => I should be evaluated
; => nil
