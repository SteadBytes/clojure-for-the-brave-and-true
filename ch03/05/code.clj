(ns ch05.ex05)

(def asym-alien-body-parts [{:name "head" :size 3}
                            {:name "eye-1" :size 1}
                            {:name "ear-1" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "neck" :size 2}
                            {:name "shoulder-1" :size 3}
                            {:name "upper-arm-1" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "forearm-1" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "kidney" :size 1}
                            {:name "hand-1" :size 2}
                            {:name "knee-1" :size 2}
                            {:name "thigh-1" :size 4}
                            {:name "lower-leg-1" :size 3}
                            {:name "achilles-1" :size 1}
                            {:name "foot-1" :size 2}])

(defn matching-parts
  "Expects a body part and a symmetry number. Returns coll of n-symmetry
   parts renamed to indicate part number.

   Ex:
    (matching-parts {:name " foot-1 " :size 2} 2)
    ; => ({:name " foot-1 " :size 2} {:name " foot-2 " :size 2})"
  [part n-symmetry]
  (map
   (fn [n] {:name (clojure.string/replace (:name part) #"-1$" (str "-" n))
            :size (:size part)})
   (range 1 (inc n-symmetry))))

(defn symmetrize-body-parts
  "Expects a seq of maps with :name and :size and the symmetry number
   i.e. 2 for humans, 5 for aliens with radial symmetry"
  [asym-body-parts n-symmetry]
  (reduce
   (fn [final-body-parts part]
     (into final-body-parts
           (set (flatten [part (matching-parts part n-symmetry)]))))
   []
   asym-body-parts))

(def alien-body-parts (symmetrize-body-parts asym-alien-body-parts 5))