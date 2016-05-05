(ns minions.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [minions.db.core :as db]
            [minions.routes.auth :refer [auth-routes]]))

(s/defschema Minion {:id Long
                     :name String
                     (s/optional-key :timestamp) (s/maybe String)})

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Minions API"
                           :description "Organize that minions!"
                           :contact {:name "Martin Balfanz"}}
                    :tags [{:name "minions" :description "Minions API"}]}}}
  auth-routes
  (context "/api" []
           (context "/v1/minions" []
                    :tags ["minions"]

                    (GET "/" []
                         :return [Minion]
                         :summary "Get all minions."
                         (ok (db/get-minions)))

                    (GET "/:id" []
                         :path-params [id :- Long]
                         :return Minion
                         :summary "Get a minion."
                         (if-let [m (db/get-minion {:id id})]
                           (ok m)
                           (not-found)))

                    (POST "/" []
                          :query-params [name :- String]
                          :return Long
                          :summary "Create a minion."
                          (ok (db/create-minion! {:name name})))

                    (PUT "/:id" []
                         :path-params [id :- Long]
                         :query-params [name :- String]
                         :return Long
                         :summary "Update a minion."
                         (ok (db/update-minion! {:id id
                                                 :name name})))

                    (DELETE "/:id" []
                            :path-params [id :- Long]
                            :return Long
                            :summary "Delete a minion."
                            (ok (db/delete-minion! {:id id}))))))
