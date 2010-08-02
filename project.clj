; tested on leiningen 1.3.0-snapshot

(defproject cleancr "0.2"
  :description "Finds and optionally removes CR (carriage return) characters from your project text files."
  :dependencies [[org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]]

  :jar-dir "dist/"
  :jar-name "cleancr-orphan.jar"
  :uberjar-name "cleancr.jar"
  
  :main cleancr)
