(ns minions.auth
  (:require [minions.db.core :as db]
            [buddy.hashers :as hs]
            [buddy.sign.jws :as jws]
            [buddy.sign.util :as util]
            [minions.config :refer [env]]))

(def ^:dynamic *secret* (:secret env))

(defn create-user!
  "Create a new user."
  [username password]
  (let [pw (hs/encrypt password)]
    (db/create-user! {:username username :password pw})))

(defn auth-user
  "Authenticate user"
  [username password]
  (let [user (db/get-user-by-username {:username username})
        unauthed [false {:message "invalid username or password"}]]
    (if user
      (if (hs/check password (:password user))
        [true {:user (dissoc user :password)}]
        unauthed)
      unauthed)))

(defn create-auth-token
  "Create auth token, valid for 10d."
  [username password]
  (let [[ok? res] (auth-user username password)
        expires (+ (util/timestamp)
                   (* 60 60 24 10))]
    (if ok?
      [true {:token (jws/sign res
                              *secret*
                              {:exp expires})}]
      [false res])))

(defn unsign-token
  "Validate token"
  [token]
  (jws/unsign token *secret*))
