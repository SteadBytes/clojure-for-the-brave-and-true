(ns fwpd.core
  (:gen-class))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})
(def validators {:name string? :glitter-index #(> % 0)}) ; ex 3

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         ; build up map of converted values
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row))) ; seq of key-val pairs
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def records (mapify (parse (slurp filename))))

; ex 1
(defn names
  "Return a list of suspect names"
  [records]
  (map #(% :name) records))

(names (glitter-filter 3 records))

; ex 2
(defn append
  "Append a new suspect. Expects a map of :name and :glitter-index"
  [records suspect]
  (when (validate validators suspect) ; ex 3
    (conj records suspect)))

(append records {:name "Jeff" :glitter-index 4})

; ex 3
(defn validate
  [validators record]
  (every?
   true?
   (map #((validators %) (record %))
        vamp-keys)))

; ex 4

; generic map-> csv conversion functions
(defn map->csv-row
  "Convert map to a CSV row string. keys specify order of columns"
  [row-map keys]
  (clojure.string/join "," (map #(row-map %) keys)))

(defn maps->csv
  "Convert seq of maps to a CSV string. keys specify order of columns"
  [maps keys]
  (clojure.string/join "\n" (map #(map->csv-row % keys) maps)))

; vampire database specific csv conversion functions
(defn unparse
  "Convert vampire database record map into CSV row string."
  [record]
  (map->csv-row records vamp-keys))

(defn unmapify
  "Convert vampire records database into CSV string."
  [records]
  (maps->csv records vamp-keys))

; demonstrate adding a new suspect and re-serialising the database
(unmapify (append records {:name "Jeff" :glitter-index 4}))
