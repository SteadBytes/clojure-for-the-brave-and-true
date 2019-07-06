(ns ch07.ex02)

(def precedence {'+ 1 '- 1 '* 2 '/ 2})

(defn inf-iter
  [expr operands operators]
  (cond
    (and (empty? expr) (empty? operators)) (first operands)
    (and ((complement empty?) expr)
         (or (empty? operators)
             (> (precedence (first expr))
                (precedence (first operators)))))
    (inf-iter (rest (rest expr))
              (cons (infix->prefix (first (rest expr))) operands)
              (cons (first expr) operators))
    :else (inf-iter expr (cons (list (first operators)
                                     (first (rest operands))
                                     (first operands))
                               (rest (rest operands)))
                    (rest operators))))

(defn infix->prefix
  [expr]
  (cond ((complement seq?) expr) expr
        (= (count expr) 1) (infix->prefix (first expr))
        :else (inf-iter (rest expr)
                        (cons (infix->prefix (first expr)) nil)
                        nil)))

(eval (infix->prefix '(1 + 3 * 4 - 5)))