; Finds and optionally removes carriage return characters from your project text files (also known as Ctrl-M, ^M, \r).

; Perhaps TODO:
; * refactor
;   * use multimethods
;   * add function docs
;   * add private access modifiers
;   * split into logic and ui files?
; * try to develop a true glob based on http://download.oracle.com/javase/tutorial/essential/io/find.html
; * tests

(ns cleancr
  (:use clojure.contrib.str-utils qertoip istext)
  (:import java.io.File java.io.FileInputStream java.io.FileOutputStream)
  (:gen-class))

(defn cr? [b]
  (= 13 b))

(defn count-crs [coll]
  (count (filter cr? coll)))

(defn count-crs-in-file [file]
  (count-crs (read-bin-file file)))

(defn print-found-crs [results]
  (if (empty? results)
    (println "No CR characters found.")
    (doseq [file_cr results]
      (println
        (format "%6d" (second file_cr))
        "CR in"
        (.getAbsolutePath (first file_cr))))))

(defn select-crs [vec]
  (select #(> (second %) 0) vec))

(defn report-cr-in-files [files]
  (let [results
         (select-crs
           (map #(vector % (count-crs-in-file %)) files))]
    (print-found-crs results)
    results))

(defn help []
  (println "Specify a file, a wildcard or a directory. Examples:")
  (println "java -jar cleancr \"/some/project/docs.txt\"")
  (println "java -jar cleancr \"/some/project/*.txt\"")
  (println "java -jar cleancr \"/some/project\"" )
  (println "java -jar cleancr \"/some/project\" --force    # won't ask for confirmation"))

(defn extension-matches? [file ext]
  (.endsWith (.getAbsolutePath file) ext))

(defn dotfile? [file]
  (let [dot_in_path (re-pattern (str ".*" "\\" (File/separator) "\\." ".*"))  ; matches something/.something
        filepath    (.getAbsolutePath file)]
    (re-matches dot_in_path filepath)))

(defn dir-textfiles [dir extension]
  (select text-file?
    (select #(extension-matches? % extension)
      (select #(not (dotfile? %))
        (select (memfn isFile)
          (file-seq dir))))))

(defn wildcard-textfiles [wildcard]
  (let [path_and_ext (seq (.split (.getAbsolutePath wildcard) "\\*"))
        path         (File. (first path_and_ext))
        extension    (second path_and_ext)]
    (dir-textfiles path extension)))

(defn remove-cr-from-file [file]
  (let [all-bytes-array   (read-bin-file file)
        all-bytes-count   (alength all-bytes-array)
        clean-bytes-seq   (filter #(not (= 13 %)) all-bytes-array)
        clean-bytes-count (- all-bytes-count (count-crs all-bytes-array))
        changed?          (< clean-bytes-count all-bytes-count)]
    (if changed?
      (write-bin-file file clean-bytes-seq clean-bytes-count))))

(defn remove-cr-from-files [files]
  (remove nil? (map remove-cr-from-file files)))

(defn report-and-remove-cr-from-files [files force?]
  (if (not (empty? (report-cr-in-files files)))
    (if (or force? (user-confirmed? "Remove CR characters from the above files (y/N)?"))
      (doseq [file (remove-cr-from-files files)]
        (println (str "Removed CR characters from " (.getAbsolutePath file))))
      (println "Files have NOT been changed."))))

(defn report-and-remove-cr-from-path [path-string force?]
  (println (str "About to remove CR characters from " path-string "..."))
  (let [path (File. path-string)]
    (if (.isDirectory path)
      (report-and-remove-cr-from-files (dir-textfiles path "") force?)
      (if (.isFile path)
        (report-and-remove-cr-from-files (vector path) force?)
        (report-and-remove-cr-from-files (wildcard-textfiles path) force?)))))

(defn -main [& args]
  (if (empty? args)
    (help)
    (let [path   (first args)
          force? (= (second args) "--force")]
      (report-and-remove-cr-from-path path force?))))

;(-main "e:/kontomierz" "--force")
