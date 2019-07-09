(ns ch09.ex02)


(defn web-search
  "Search for `query` using `search-urls`, returns first page of results
   of first query to finish. `search-urls` should be a seq of URLs
   where appending `query` to the end results ina valid search URL."
  [query search-urls]
  (let [result (promise)]
    (doseq [url search-urls]
      (future (deliver result (slurp (str url query)))))
    @result))

(web-search "clojure"
            ["https://google.com/search?q%3D"
             "https://www.bing.com/search?q%3d"])