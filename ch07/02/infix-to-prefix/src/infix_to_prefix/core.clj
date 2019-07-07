(ns infix-to-prefix.core
  (:gen-class)
  (:require clojure.set))

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

(defn -main
  "Convert infix mathematical expressions to prefix. Provide --eval option to
   evaluate converted expression.

   Ex:
      infix-to-prefix (1 + 2 * 3)
      (+ 1 (* 2 3))

      infix-to-prefix (1 + 2 * 3) --eval
      7
   Note: No real error checking, misformed input expressions will cause exceptions"
  [& args]
  (let [options (set (filter #(clojure.string/starts-with? % "-") args))
        exprs (filter  #((complement contains?) options %) args)]
    (if (or (empty? exprs) (contains? options "-h"))
      (println "Usage: infix-to-prefix --eval EXPR1 EXPR2...")
      (let [prefix-exprs (map #(infix->prefix (read-string %)) exprs)
            eval? (some #(= % "--eval") options)]
        (doseq [prefix prefix-exprs]
          (if eval?
            (println (eval prefix))
            (println prefix)))))))
