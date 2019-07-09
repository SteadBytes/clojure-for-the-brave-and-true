(ns ch09.ex01)

(def search-urls ["https://google.com/search?q%3D" "https://www.bing.com/search?q%3d"])

(defn web-search
  "Search for `query` using Bing and Google, returns first page of results
   of first query to finish"
  [query]
  (let [result (promise)]
    (doseq [url search-urls]
      (future (deliver result (slurp (str url query)))))
    @result))

(web-search "clojure")