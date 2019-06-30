(ns ch05.ex04)

(defn mapset
  [f seq]
  (set (map f seq)))

(mapset inc [1 1 2 2])