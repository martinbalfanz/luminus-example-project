(ns user
  (:require [mount.core :as mount]
            [minions.figwheel :refer [start-fw stop-fw cljs]]
            minions.core))

(defn start []
  (mount/start-without #'minions.core/repl-server))

(defn stop []
  (mount/stop-except #'minions.core/repl-server))

(defn restart []
  (stop)
  (start))


