(ns minions.test.db.core
  (:require [minions.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [minions.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'minions.config/env
      #'minions.db.core/*db*)
    (migrations/migrate ["migrate"] (env :database-url))
    (f)))

(deftest test-minions
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-minion!
              t-conn
              {:name "test"})))
    (is (= {:id 1
            :name "test"
            :timestamp nil}
           (db/get-minion t-conn {:id 1})))
    (is (= 1 (db/update-minion!
              t-conn
              {:id 1
               :name "updated"})))
    (is (= {:id 1
            :name "updated"
            :timestamp nil}
           (db/get-minion t-conn {:id 1})))
    (is (= 1 (db/delete-minion!
              t-conn
              {:id 1})))
    (is (nil? (db/get-minion t-conn {:id 1})))))
