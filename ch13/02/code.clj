(ns ch13.ex02)

(defprotocol WereCreature
  (full-moon-behaviour [x]))

(defrecord WereWolf [name title]
  WereCreature
  (full-moon-behaviour [x]
    (str name " will howl and murder")))

(defrecord WereSimmons [name title]
  WereCreature
  (full-moon-behaviour [x]
    (str name " will encourage people and sweat to the oldies")))

(full-moon-behaviour (map->WereWolf {:name "Lucian" :title "CEO of Melodrama"}))
; => "Lucian will howl and murder"

(full-moon-behaviour (map->WereSimmons {:name "Simmons" :title "Sweatin' King"}))
; => "Simmons will encourage people and sweat to the oldies"