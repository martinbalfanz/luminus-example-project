-- :name create-minion! :! :n
-- :doc creates a new minion record
INSERT INTO minions
(name)
VALUES (:name)

-- :name update-minion! :! :n
-- :doc update an existing minion record
UPDATE minions
SET name = :name
WHERE id = :id

-- :name get-minions :? :*
-- :doc retrieve a list of all minions

select * from minions

-- :name get-minion :? :1
-- :doc retrieve a minion given the id
SELECT * FROM minions
WHERE id = :id

-- :name delete-minion! :! :n
-- :doc delete a minion given the id
DELETE FROM minions
WHERE id = :id
