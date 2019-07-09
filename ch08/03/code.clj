(ns ch08.ex03)

(defmacro defattrs
  ([] nil)
  ([fname attr]
   `(def ~fname (comp ~attr :attributes)))
  ([fname attr & rest]
   `(do (defattrs ~fname ~attr)
        (defattrs ~@rest))))

(defattrs
  c-int :intelligence
  c-str :strength
  c-dex :dexterity)

(def character
  {:name "Smooche McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(c-int character)
; => 10

(c-str character)
; => 4

(c-dex character)
; => 5

