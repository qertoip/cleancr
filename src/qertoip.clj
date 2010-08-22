(ns qertoip
  (:use
    [clojure.contrib.io :only [to-byte-array]]
    [clojure.contrib.java-utils :only [as-file]])
  (:import
    [java.io File FileOutputStream]))

; general - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

(def select filter) ; because the 'filter' name sucks

; files - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

(def read-file slurp)

(def write-file spit)

(defn read-bin-file [file]
  (to-byte-array (as-file file)))

(defn write-bin-file
  ([file coll]
    (with-open [out (FileOutputStream. file)]
      (do
        (.write out (byte-array coll))
        file)))
  ([file coll size]
    (with-open [out (FileOutputStream. file)]
      (do
        (.write out (byte-array size coll))
        file))))

; ui - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

(defn user-confirmed? [message]
  (print (str message " "))
  (flush)
  (let [c (read-line)]
    (= "y" c)))
