(ns ch09.ex03)

(defn fetch
  "Returns a future to fetch contents of URL"
  [url]
  (let [result (promise)]
    (future (slurp url))))

(defn web-search
  "Search for `query` using `search-url`, returns first page of results.
  `search-url` should be a URL where appending `query` results in a
  valid search URL for `query`"
  [query search-url]
  (fetch  (str search-url query)))

(defn extract-urls
  "Extract all URLs from a string"
  [s]
  (re-seq #"https?://[^\"]*" s))

(defn multi-web-search
  "Return URLs from first page of search results obtained by searching for `query`
   using URL in `search-urls`. `search-urls` should be a seq of URLs where appending
   `query` results in a valid search URL for `query`"
  [query search-urls]
  (let [search-futures (map #(web-search query %) search-urls)
        urls (map #(extract-urls (deref % 1000 "")) search-futures)]
    (vec (flatten urls))))


(def search-urls ["https://www.google.com/search?q%3D"
                  "https://www.bing.com/search?q%3D"
                  "https://archive.org/search.php?query%3D"])

(multi-web-search "clojure" search-urls)