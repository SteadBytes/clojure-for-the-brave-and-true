(ns ch05.ex01)

; You used (comp :intelligence :attributes) to create a function that
; returns a character’s intelligence. Create a new function, attr , that you can
; call like (attr: intelligence) and that does the same thing.

(def character
  {:name "Smooche McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

; function given in book
((comp :intelligence :attributes) character)
; => 10

(defn attr
  "Returns a function to which returns attribute of a character
   Ex:
     ((attr: intelligence) {:name \"Frodo\" :attributes {:intelligence 10}})
     ; => 10"
  [attribute]
  (comp attribute :attributes))

((attr :intelligence) character)
; => 10

