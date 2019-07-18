(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))

(defn hot-dog-machine
  []
  (let [in (chan) ; channel for receiving money
        out (chan)] ; channel for dispensing hot dogs
    (go (<! in)
        (>! out "hotdog"))
    [in out]))



(let [[in out] (hot-dog-machine)]
  (>!! in "pocket lint") ; put onto in channel
  (<!! out)) ; machine processes input and puts "hot dog" onto out channel
; => "hotdog"

(defn hot-dog-machine-v2
  "Dispense hot-dog-count hot dogs at 3 dollars each, other input values result
   in wilted lettuce"
  [hot-dog-count]
  (let [in (chan)
        out (chan)]
    (go (loop [hc hot-dog-count]
          (if (> hc 0)
            ; process pipeline (put and take within same go block)
            ; in channel of one process is out channel of another
            (let [input (<! in)]
              (if (= 3 input)
                (do (>! out "hotdog")
                    (recur (dec hc)))
                (do (>! out "wilted lettuce")
                    (recur hc))))
            (do (close! in)
                (close! out)))))
    [in out]))

(let [[in out] (hot-dog-machine-v2 2)] ; 2 hot dogs in machine
  (>!! in "pocket lint")
  (println (<!! out))
  ; => wilted lettuce

  (>!! in 3)
  (println (<!! out))
  ; => hotdog

  (>!! in 3)
  (println (<!! out))
  ; => hotdog

  (>!! in 3)
  (println (<!! out))) ; machine run out of hot dogs (channel closed)
  ; => nil


;; alts!!
;; use result of first successful channel operation among a coll of operations

(defn upload
  [headshot c]
  (go (Thread/sleep (rand 100)) ; simulate 'upload'
      (>! c headshot)))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (upload "serious.jpg" c1)
  (upload "fun.jpg" c2)
  (upload "sassy.jpg" c3)
  (let [[headshot channel] (alts!! [c1 c2 c3])]
    ; print whichever headshot finished processing first
    ; other channels still available to take values from
    (println "Sending headshot notification for" headshot)))
; => Sending headshot notification for sassy.jpg

; alts!! timeout channel
(let [c1 (chan)]
  (upload "serious.jpg" c1)
  (let [[headshot channel] (alts!! [c1 (timeout 20)])] ; set timeout of 20 ms
    (if headshot
      (println "Sending headshot notification for" headshot)
      (println "Timed out!"))))
; => Timeout out!

; alts!! specify put operations
(let [c1 (chan)
      c2 (chan)]
  (go (<! c2)) ; process waiting to perform take on c2
  ; try to do take on c1 and try to "put!" on c2
  ; if take on c1 finishes first return it's value and channel
  ; if put on c2 finished first return true if successful else false
  (let [[value channel] (alts!! [c1 [c2 "put!"]])]
    (println value)
    (= channel c2)))
; => true ; c2 finished first
; => true

;; Queues

(defn append-to-file
  "Write a string to the end of a file"
  [filename s]
  (spit filename s :append true))

(defn format-quote
  "Delineate the beginning and end of a quote"
  [quote]
  (str "=== BEGIN QUOTE ===\n" quote "=== END QUOTE ===\n\n"))

(defn random-quote
  "Retrieve a randome quote and format it"
  []
  (format-quote (slurp "https://www.braveclojure.com/random-quote")))

(defn snag-quotes
  "Fetch num-quotes quotes and write them to filename
   Queue used to ensure text from multiple consumer processes is not interleaved
   Ex:
     (snag-quotes \"quotes.txt\" 10) ; create quotes.txt containing 10 quotes
   "
  [filename num-quotes]
  (let [c (chan)] ; channel shared between quote producer and consumer processes
    ; loop waits for quote to arrive on c and appends it to file (consumer)
    (go (while true (append-to-file filename (<! c))))
    ; create num-quote producer process which fetch quotes and place onto c
    (dotimes [n num-quotes] (go (>! c (random-quote))))))

;; Process Pipelines

(defn upper-caser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/upper-case (<! in)))))
    out))

(defn reverser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/reverse (<! in)))))
    out))

(defn printer
  [in]
  (go (while true (println (<! in)))))

; build a process pipeline to upper case and reverse strings
(def in-chan (chan)) ; channel for input strings
(def upper-caser-out (upper-caser in-chan)) ; pass channels as input to next process
(def reverser-out (reverser upper-caser-out))
(printer reverser-out)

(>!! in-chan "htlif")
; => FILTHY

(>!! in-chan "sestibboh")
; => HOBBITSES
