(ns ch13.ex01)

(defmulti full-moon-behaviour (fn [were-creature] (:were-type were-creature)))

; methods from book
(defmethod full-moon-behaviour :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behaviour :simmons
  [were-creature]
  (str (:name were-creature) " will encourage people and sweat to the oldies"))

(defmethod full-moon-behaviour nil
  [were-creature]
  (str (:name were-creature) " will stay at home and eat ice cream"))

(defmethod full-moon-behaviour :default
  [were-creature]
  (str (:name were-creature) " will stay up all night fantasy footballing"))

; extension for exercise
(defmethod full-moon-behaviour :hobbit
  [were-creature]
  (str (:name were-creature) " will search for second breakfast"))

(full-moon-behaviour {:name "Frodo" :were-type :hobbit})
; => "Frodo will search for second breakfast"

























































































