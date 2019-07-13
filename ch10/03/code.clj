(ns ch10.ex03)

(def max-hp 40)

(defn generate-character
  [name hp inventory]
  {:name name
   :hp hp
   :inventory inventory})

(defn display-character
  [character]
  (println (str (character :name) ":"))
  (println "\tHP =>" (character :hp))
  (println "\tInventory => " (character :inventory)))

(def link (ref (generate-character "Link" 15 {})))
(def zelda (ref (generate-character "Princess Zelda" max-hp {:potion 1})))


(def link-before @link)
(def zelda-before @zelda)
(println "Before")
(display-character link-before)
(display-character zelda-before)
(dosync
 (alter link assoc :hp max-hp)
 (alter zelda assoc :inventory {}))
(println "After")
(display-character @link)
(display-character @zelda)

