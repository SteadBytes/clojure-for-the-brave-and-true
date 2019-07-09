(ns ch08.ex01)

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like and email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defmacro when-valid
  "Evaluates body in logical do if validation succeeds"
  [to-validate validations & body]
  `(when (empty? (validate ~to-validate ~validations))
     ~@body))

(when-valid
 {:name "Bilbo Baggins"
  :email "bilbo@hobbiton.com"}
 order-details-validations
 (println "It's a success!")
 (println :success))
; => It's a success
; => :success
; => nil

(when-valid
 {:name "Bilbo Baggins"
  :email "bilbo.hobbiton.com"} ; invalid email
 order-details-validations
 (println "It's a success!")
 (println :success))
; => nil