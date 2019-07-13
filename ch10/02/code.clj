(ns ch10.ex02)

(defn fetch
  "Returns a future to fetch contents of URL"
  [url]
  (let [result (promise)]
    (future (slurp url))))

(defn fetch-quote
  []
  (fetch "https://braveclojure.com/random-quote"))

(defn strip-punctuation
  "Remove punctuation from s"
  [s]
  (clojure.string/replace s #"[.,\/#!?$%\^&\*;:{}=\-_`~()]" ""))

(defn extract-words
  "Return seq of individual words in s (punctuation & whitespace removed)"
  [s]
  (clojure.string/split (strip-punctuation s) #"\s+"))

(defn words-count
  "Return a map of counts for each word in s
   Ex:
     (words-count \"in a hole in the ground there lived a hobbit\")
     ; => {\"in\" 2, \"a\" 2, \"hole\" 1, \"the\" 1, \"ground\" 1,
            \"there\" 1, \"lived\" 1, \"hobbit\" 1}"
  [s]
  (reduce (fn [m word]
            (if (contains? m word)
              (update m word inc)
              (assoc m word 1)))
          {}
          (extract-words s)))

(defn quote-word-count
  [n]
  (let [total-word-counts (atom {})
        update-counts (fn [to-add]
                     (swap! total-word-counts
                            #(merge-with + % to-add)))]
    (dorun
     (pmap ; timed faster than non-parallel map
      deref
      (repeatedly n #(future (update-counts (words-count @(fetch-quote)))))))
    @total-word-counts))

(quote-word-count 20)