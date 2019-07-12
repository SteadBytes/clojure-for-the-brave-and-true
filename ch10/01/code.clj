(ns ch10.ex01)

(def my-atom (atom 0))
(swap! my-atom inc)
; 1
(swap! my-atom inc)
; 2
@my-atom
; 2