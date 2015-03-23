(defproject javabin-clojure-talk "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :source-paths ["dev"]}
             ;; :uberjar {:aot [javabin-clojure-talk.bot]
             ;;           :main javabin-clojure-talk.bot}
             }
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.2"]
                 [ring/ring-core "1.3.2"]
                 [http-kit "2.1.19"]
                 [cheshire "5.4.0"]
                 [aprint "0.1.3"]
                 [enlive "1.1.5"]])
