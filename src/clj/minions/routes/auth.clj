(ns minions.routes.auth
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [minions.db.core :as db]
            [minions.auth :as auth]))

(def auth-routes
  (context "/api" []
           (context "/v1/users" []
                    :tags ["users"]

                    (GET "/" []
                         :summary "Get all users."
                         (ok (map #(dissoc % :password) (db/get-users))))

                    (GET "/:username" []
                         :path-params [username :- String]
                         :summary "Get a user by username"
                         (if-let [u (db/get-user-by-username {:username username})]
                           (ok (dissoc u :password))
                           (not-found)))

                    (POST "/" []
                          :query-params [username :- String
                                         password :- String]
                          :return Long
                          :summary "Create a user."
                          (ok (auth/create-user! username password)))
                    (POST "/auth" []
                          :query-params [username :- String
                                         password :- String]
                          :return String
                          :summary "get an auth token"
                          (let [[ok? res] (auth/create-auth-token username password)]
                            (if ok?
                              (created res)
                              (unauthorized res)))))))
