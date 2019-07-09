(ns ch09.ex02)


(defn web-search
  "Search for `s` using `search-urls`, returns first page of results
   of first query to finish. `search-urls` should be a seq of URLs
   where appending `s` to the end results ina valid search URL."
  [s search-urls]
  (let [result (promise)]
    (doseq [url search-urls]
      (future (deliver result (slurp (str url s)))))
    @result))

(web-search "clojure"
            ["https://google.com/search?q%3D"
             "https://www.bing.com/search?q%3d"])