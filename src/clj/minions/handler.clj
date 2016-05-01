(ns minions.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [minions.layout :refer [error-page]]
            [minions.routes.home :refer [home-routes]]
            [minions.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [minions.env :refer [defaults]]
            [mount.core :as mount]
            [minions.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    #'service-routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
