
(ns cleancr
  (:use clojure.contrib.duck-streams)
  (:import java.io.File)
  (:gen-class))

(def select filter)

(defn number-of-cr [filepath]
  (let [s (slurp filepath)]
    (count
      (re-seq #"\r" s)
    )
  )
)

(defn remove-cr-from-string [s]
  (.replaceAll s "\r" "")
)

(defn remove-cr-from-file [filepath]
  (let [dirty (slurp filepath)
        cleaned (remove-cr-from-string dirty)
        changed? (< (.length cleaned) (.length dirty))
       ]
    (if changed?
      (do
        (spit filepath cleaned)
        (println "Removed CR from" filepath)
      )
    )
  )
)

(defn pretty-print [results]
  (if (empty? results)
    (println "No CR found.")
    (doseq [pair_filepath_cr results]
      (println
        (first pair_filepath_cr) "CR in" (second pair_filepath_cr)
      )
    )
  )
)

(defn select-crs [vec]
  (select #(> (first %) 0) vec)
)

(defn report-cr-in-files [filepaths]
  (let [results
         (select-crs
           (map #(vector (number-of-cr %) %) filepaths)
         )
       ]
    (pretty-print results)
    results
  )
)

(defn remove-cr-from-files [filepaths]
  (doseq [filepath filepaths] (remove-cr-from-file filepath))
)

(defn help []
  (println "Specify file or directory. Examples:")
  (println "java -jar cleancr e:\\project\\docs.txt")
  (println "java -jar cleancr e:\\project")
)

(defn user-confirmed? [message]
  (print (str message " "))
  (flush)
  (let [c (read-line)]
    (= "y" c)
  )
)

(defn report-and-remove-cr-from-files [filepaths]
  (if (not (empty? (report-cr-in-files filepaths)))
    (if (user-confirmed? "Remove CR from the above files (y/N)?")
      (remove-cr-from-files filepaths)
      (println "Files have NOT been changed.")
    )
  )
)

(defn extension-matches [file ext]
  (.endsWith (.getAbsolutePath file) ext)
)

(defn get-dir-filepaths [dirpath]
  (map
    #(.getAbsolutePath %)
    (select
      #(extension-matches % "rb")
      (select
        #(.isFile %)
        (file-seq (File. dirpath))
      )
    )
  )
)

(defn report-and-remove-cr-from-path [path]
  (println (str "About to remove CR from " path "..."))
  (if (.isDirectory (File. path))
    (report-and-remove-cr-from-files (get-dir-filepaths path))
    (report-and-remove-cr-from-files (vector path))
  )
)

(defn -main [& args]
  (if (empty? args)
    (help)
    (report-and-remove-cr-from-path (first args))
  )
)

;(-main "e:/kontomierz")
