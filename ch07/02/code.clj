(ns ch07.ex02)

(def precedence {'+ 1 '- 1 '* 2 '/ 2})

(defn infix->prefix
  ([expr]
   (if ((complement seq?) expr)
     expr
     (let [[a & as] expr]
       (if (empty? as)
         (infix->prefix a)
         (infix->prefix as (list (infix->prefix a)) '())))))
  ([[a & [b & bs] :as expr]
    [opr & [opr' & oprs] :as operands]
    [op & ops :as operators]]
   (cond
     ; no expr or operators left -> return result
     (and (nil? a) (nil? op)) opr
     ; operator at top of expr has greater precedence than top operator
     ; push top of expr to top of operators
     ; convert 2nd item of expr to infix and push to top of operands
     (and ((complement nil?) a)
          (or (nil? op)
              (> (precedence a)
                 (precedence op))))
     (recur bs
            (cons (infix->prefix b) operands)
            (cons a operators))
     ; combine top operator with two top operands
     :else (recur expr (cons (list op ; construct composite operand
                                    opr'
                                    opr)
                              oprs)
                  ops))))

(eval (infix->prefix '(1 + 3 * 4 - 5)))
; => 8