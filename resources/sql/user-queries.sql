-- :name create-user! :! :n
-- :doc create a new user record
insert into users
    (username, password)
    values (:username, :password)

-- :name get-users :? :*
-- :doc retrieve a user given the id
select id, username from users

-- :name get-user :? :1
-- :doc retrieve a user given the id
select * from users
where id = :id

-- :name get-user-by-username :? :1
-- :doc retrieve a user given the username
select * from users
where username = :username

-- :name update-user! :! :n
-- :doc update an existing user
update users
set username = :username, password = :password
where id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
delete from users
where id = :id
