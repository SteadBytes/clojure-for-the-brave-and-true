(ns ch13.ex02)

(defprotocol Saveable
  (collection-name [x]
    "return string representing collection under which to save record"))

(defrecord Hobbit [name age]
  Saveable
  (collection-name [x] :hobbit))

(defrecord Elf [name age]
  Saveable
  (collection-name [x] :elf))

(def map-db (atom {}))

(defn save [x] (swap! map-db update-in [(collection-name x)] conj x))

(println @map-db)
(save (Hobbit. "Bilbo" 111))
(println @map-db)
(save (Hobbit. "Frodo" 33))
(println @map-db)
(save (Elf. "Legolas" 2931))
(println @map-db)

; {}

; {:hobbit (#ch13.ex02.Hobbit{:name Bilbo, :age 111})}

; {:hobbit (#ch13.ex02.Hobbit{:name Frodo, :age 33} #ch13.ex02.Hobbit{:name Bilbo, :age 111})}

; {:hobbit (#ch13.ex02.Hobbit{:name Frodo, :age 33} #ch13.ex02.Hobbit{:name Bilbo, :age 111}), :elf (#ch13.ex02.Elf{:name Legolas, :age 2931})}