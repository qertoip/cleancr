(ns qertoip
  (:use clojure.contrib.duck-streams)
  (:import java.io.File))

(def select filter)

(def read-file slurp)

(def write-file spit)

(defn user-confirmed? [message]
  (print (str message " "))
  (flush)
  (let [c (read-line)]
    (= "y" c)))
