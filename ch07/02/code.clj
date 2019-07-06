(ns ch07.ex02)

(def precedence {'+ 1 '- 1 '* 2 '/ 2})

(defn infix->prefix
  [expr]
  (cond ((complement seq?) expr) expr
        (= (count expr) 1) (infix->prefix (first expr))
        :else
        (loop [expr' (rest expr)
               operands (list (infix->prefix (first expr)))
               operators '()]
          (cond
            ; no expr or operators left -> return result
            (and (empty? expr') (empty? operators)) (first operands)
            ; operator at top of expr has greater precedence than top operator
            ; push top of expr to top of operators
            ; convert 2nd item of expr to infix and push to top of operands
            (and ((complement empty?) expr')
                 (or (empty? operators)
                     (> (precedence (first expr'))
                        (precedence (first operators)))))
            (recur (rest (rest expr'))
                   (cons (infix->prefix (first (rest expr'))) operands)
                   (cons (first expr') operators))
            ; combine top operator with two top operands
            :else (recur expr' (cons (list (first operators) ; construct composite operand
                                           (first (rest operands))
                                           (first operands))
                                     (rest (rest operands)))
                         (rest operators))))))

(eval (infix->prefix '(1 + 3 * 4 - 5)))
; => 8