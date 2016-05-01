(ns minions.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [minions.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[minions started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[minions has shutdown successfully]=-"))
   :middleware wrap-dev})
