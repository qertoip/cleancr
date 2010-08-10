(ns qertoip
  (:use clojure.contrib.duck-streams clojure.contrib.java-utils)
  (:import java.io.File java.io.FileOutputStream))

; general - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

(def select filter) ; because the 'filter' name sucks

(def read-file slurp)

; files - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

(def write-file spit)

(defn write-bin-file [file col]
  (with-open [out (FileOutputStream. file)]
    (.write out (byte-array col))))

(defn read-bin-file [file]
  (to-byte-array (as-file file)))

; ui - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

(defn user-confirmed? [message]
  (print (str message " "))
  (flush)
  (let [c (read-line)]
    (= "y" c)))
