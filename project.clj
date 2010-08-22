; tested on leiningen 1.3.0-snapshot

(defproject cleancr "1.0-beta2"
  :description "Finds and optionally removes carriage return characters from your project text files (also known as Ctrl-M, ^M, \r)."
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [lein-run "1.0.0-SNAPSHOT"]
                 [org.clojars.qertoip/istext "1.0"]]

  :jar-dir "dist/"
  :jar-name "cleancr-lib.jar"
  :uberjar-name "cleancr.jar"

  :run-aliases {:ccr [cleancr -main]}

  :main cleancr)
