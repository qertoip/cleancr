; Finds and optionally removes CR (carriage return aka \r aka ^M) characters from your project text files.
;
; TODO:
; * improve text files recognition
; * add --force option
; * tests
; * final help and readme

(ns cleancr
  (:use qertoip)
  (:import java.io.File java.io.FileInputStream)
  (:gen-class))

(defn crs-in-string [s]
  (count (re-seq #"\r" s)))

(defn crs-in-file [file]
  (crs-in-string (read-file (.getAbsolutePath file))))

(defn remove-cr-from-string [s]
  (.replaceAll s "\r" ""))

(defn remove-cr-from-file [file]
  (let [filepath (.getAbsolutePath file)
        dirty    (read-file filepath)
        cleaned  (remove-cr-from-string dirty)
        changed? (< (.length cleaned) (.length dirty))]
    (if changed?
      (do
        (write-file filepath cleaned)
        file)
      nil)))

(defn remove-cr-from-files [files]
  (remove nil? (map remove-cr-from-file files)))


(defn print-found-crs [results]
  (if (empty? results)
    (println "No CR characters found.")
    (doseq [file_cr results]
      (println
        (second file_cr) "CR in" (.getAbsolutePath (first file_cr))))))

(defn select-crs [vec]
  (select #(> (second %) 0) vec))

(defn report-cr-in-files [files]
  (let [results
         (select-crs
           (map #(vector % (crs-in-file %)) files))]
    (print-found-crs results)
    results))

(defn help []
  (println "Specify file or directory. Examples:")
  (println "java -jar cleancr e:\\project\\docs.txt")
  (println "java -jar cleancr e:\\project"))

;(defn extension-matches [file ext]
;  (.endsWith (.getAbsolutePath file) ext)
;)

(defn text-file? [file]
  (let [buffer (byte-array 1024)
        stream (FileInputStream. file)
        size   (.read stream buffer)
        leading_bytes  (take size buffer)]
    (every? #(not (zero? %)) leading_bytes)))

(defn dotfile? [file]
  (let [dot_in_path (re-pattern (str ".*" "\\" (File/separator) "\\." ".*"))  ; matches something/.something
        filepath    (.getAbsolutePath file)]
    (re-matches dot_in_path filepath)))

(defn dir-textfiles [dir]
  (select text-file?
    (select #(not (dotfile? %))
      (select (memfn isFile)
        (file-seq dir)))))

(defn report-and-remove-cr-from-files [files]
  (if (not (empty? (report-cr-in-files files)))
    (if (user-confirmed? "Remove CR characters from the above files (y/N)?")
      (doseq [file (remove-cr-from-files files)]
        (println (str "Removed CR characters from " (.getAbsolutePath file))))
      (println "Files have NOT been changed."))))

(defn report-and-remove-cr-from-path [path_string]
  (println (str "About to remove CR characters from " path_string "..."))
  (let [path (File. path_string)]
    (if (.isDirectory path)
      (report-and-remove-cr-from-files (dir-textfiles path))
      (report-and-remove-cr-from-files (vector path)))))

(defn -main [& args]
  (if (empty? args)
    (help)
    (report-and-remove-cr-from-path (first args))))

;(-main "e:/kontomierz/app")
