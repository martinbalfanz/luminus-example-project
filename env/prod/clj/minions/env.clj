(ns minions.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[minions started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[minions has shutdown successfully]=-"))
   :middleware identity})
