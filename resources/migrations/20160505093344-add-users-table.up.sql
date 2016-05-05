create table users
    (id integer primary key autoincrement,
        username varchar(30) unique not null,
        password varchar(255) not null);
