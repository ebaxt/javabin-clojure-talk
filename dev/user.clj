(ns user
  (:require

   [clojure.java.io :as io]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as str :refer [upper-case split]]
   [clojure.tools.namespace.repl :refer [refresh refresh-all]]

   [clojure.pprint :refer [pprint]]
   [org.httpkit.client :as client]
   [net.cgrand.enlive-html :as html]
   [net.cgrand.tagsoup :as tagsoup]
   [javabin-clojure-talk.bot :refer :all]

   [aprint.core :refer :all]

   [clojure.walk :refer [macroexpand-all]]
   [cheshire.core :as json]))

(def system
  "A Var containing an object representing the application under
  development."
  (atom {}))

(defn start
  "Starts the system running, updates the Var #'system."
  []
  (reset! system {:server (start-server {:config {:base-url "http://localhost:8080"}
                                         :port 4000})}))

(defn stop
  "Stops the system if it is currently running, updates the Var
  #'system."
  []
  (when-let [s (:server @system)]
    (s)))

(defn go
  "Initializes and starts the system running."
  []
  (start)
  :ready)

(defn reset
  "Stops the system, reloads modified source files, and restarts it."
  []
  (stop)
  (refresh :after 'user/go))



(def queries ["ardoq"
              "visualizing Swagger"
              "documentation platform"
              "automated documentation"])

