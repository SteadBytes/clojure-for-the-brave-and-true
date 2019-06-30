(ns ch05.ex03)

; Implement the assoc-in function. Hint: use the assoc function and define its
; parameters as [m [k & ks] v].


; examples of assoc-in
(def characters [{:name "Meriadoc Brandybuck"
                  :attributes {:intelligence 7
                               :strength 4
                               :dexterity 5}}
                 {:name "Frodo Baggins"
                  :attributes {:intelligence 6
                               :strength 4
                               :dexterity 4}}
                 {:name "Peregrin Took"
                  :attributes {:intelligence 4
                               :strength 5
                               :dexterity 5}}])

(def frodo (characters 1))
; update existing nested key (map)
(assoc-in frodo [:attributes :strength] 3)

; create new nested key (vector of maps)
(assoc-in frodo [:items :weapons] {:type "sword" :name "Sting"})

; update existing nested key (vector of maps)
(assoc-in characters [1 :attributes :strength] 3)

; create new nested key (vector of maps)
(assoc-in characters [1 :items :weapons] {:type "sword" :name "Sting"})

; re-implementation
(defn assoc-in'
  [m [k & ks] v]
  (if (empty? ks)
    ; no extra keys -> associate value with key
    (assoc m k v) 
    ; extra keys -> associate current key with result of associating remaining keys
    (assoc m k (assoc-in (m k) ks v))))

; update existing nested key (map)
(assoc-in' frodo [:attributes :strength] 3)

; create new nested key (vector of maps)
(assoc-in' frodo [:items :weapons] {:type "sword" :name "Sting"})

; update existing nested key (vector of maps)
(assoc-in' characters [1 :attributes :strength] 3)

; create new nested key (vector of maps)
(assoc-in' characters [1 :items :weapons] {:type "sword" :name "Sting"})