(ns ch05.ex04)

; Look up and use the update-in function

(def hobbits [{:name "Meriadoc Brandybuck"
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

(def men [{:name "Aragorn, son of Arathorn"
           :attributes {:intelligence 7
                        :strength 8
                        :dexterity 8}}
          {:name "Boromir, son of Denethor"
           :attributes {:intelligence 6
                        :strength 9
                        :dexterity 6}}
          {:name "Theoden, sone of Thengel"
           :attributes {:intelligence 6
                        :strength 7
                        :dexterity 5}}])

(defn buff-maker
  "Returns a function to apply a buff to a character attribute, taking either a
   single argument of a character map or a map and a list of keys to traverse
   to find the desired character"
  [attribute f]
  (fn
    ([m] (update-in m [:attributes attribute] f))
    ([m ks] (update-in m (concat ks [:attributes attribute]) f))))

(def strength-boost (buff-maker :strength (partial + 10)))

(def frodo (characters 1))
; update existing nested key (map)
(strength-boost frodo)

(def characters-by-race {:hobbits hobbits :men men})

(strength-boost characters-by-race [:hobbits 0])

; re-implementation
(defn update-in'
  [m ks f & args]
  (let [update
        (fn update [m ks f args]
          (let [[k & ks] ks]
            (if (empty? ks)
              ; no extra keys -> fetch value apply f and associate new value
              (assoc m k (apply f (m k) args))
              ; extra keys -> associate current key with result up updating remaining keys
              (assoc m k (update (m k) ks f args)))))]
    (update m ks f args)))

(defn buff-maker'
  "Returns a function to apply a buff to a character attribute, taking either a
   single argument of a character map or a map and a list of keys to traverse
   to find the desired character"
  [attribute f]
  (fn
    ([m] (update-in' m [:attributes attribute] f))
    ([m ks] (update-in' m (concat ks [:attributes attribute]) f))))

(def strength-boost' (buff-maker' :strength (partial + 10)))

(strength-boost' frodo)

(def characters-by-race {:hobbits hobbits :men men})

(strength-boost' characters-by-race [:hobbits 0])