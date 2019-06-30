(ns ch04.reduce)

; pg 81: "try implementing map using reduce and then do the same for filter 
; after you read about them later in this chapter"

; implement map using reduce (basic single collection)
(defn map'
  [f coll]
  (reduce #(conj %1 (f %2)) [] coll))

(assert (= (map' inc [1 2 3 4 5]) (map inc [1 2 3 4 5])))

; implement filter using reduce
(defn filter'
  [pred coll]
  (reduce
   (fn [acc val]
     (if (pred val)
       (conj acc val)
       acc))
   []
   coll))

(assert (= (filter' even? [1 2 3 4 5])
           (filter even? [1 2 3 4 5])))

; implement some using reduce
(defn some'
  [pred coll]
  (reduce
   (fn [acc val]
     (let [pred-val (pred val)]
       (if pred-val
         (reduced pred-val))))
   coll))

(assert (= (some' even? [1 2 3 4 5])
           (some even? [1 2 3 4 5])))

(assert (= (some' #(and (even? %) %) [1 2 3 4 5])
           (some #(and (even? %) %) [1 2 3 4 5])))

(some' odd? [2 4 6 8])