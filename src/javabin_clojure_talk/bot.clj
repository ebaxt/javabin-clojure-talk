(ns javabin-clojure-talk.bot
  (:require [clojure.pprint :refer [pprint]]
            [org.httpkit.server :refer [run-server]]
            [org.httpkit.client :as client]
            [net.cgrand.enlive-html :as html]
            [cheshire.core :as json]
            [compojure.core :refer [routes GET POST]]
            [compojure.route :as route]))


(defn query! [q]
  {:query q
   :results (keep-indexed
             (fn [idx {{:keys [href]} :attrs}]
               (when (.contains href  "ardoq")
                 {:rank (inc idx)
                  :link href}))
             (-> "https://duckduckgo.com/html?q="
                 (str (java.net.URLEncoder/encode q))
                 (client/get)
                 (deref)
                 :body
                 (html/html-snippet)
                 (html/select [:div.results_links :a.large])))})

;; Never use read-string like this!
(defn parse-input [{:keys [body]}]
  (-> body
      slurp
      read-string))

(defn app [system]
  (routes
   (POST "/queries" request
         {:status 200
          :headers {"Content-Type" "application/json"}
          :body (json/encode (map query! (parse-input request)))})))

(defn start-server [system]
  (run-server (app system) system))
