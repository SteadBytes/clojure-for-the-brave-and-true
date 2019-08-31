(def alphabet-length 26)

; vector of chars A-Z
(def letters (mapv (comp str char (partial + 65)) (range alphabet-length)))

(defn random-string
  "Return random string of specified length"
  [length]
  (apply str (take length (repeatedly #(rand-nth letters)))))

(defn random-strings
  "Return list of n random strings of specified length"
  [n length]
  (doall (take n (repeatedly (partial random-string length)))))

(def orc-names (random-strings 3000 7000))

; slow
(time (dorun (map clojure.string/lower-case orc-names)))
; fast
(time (dorun (pmap clojure.string/lower-case orc-names)))