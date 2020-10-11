create table post(
                     id serial primary key,
                     name varchar(300),
                     text text,
                     link varchar(2083),
                     created timestamp,
                     UNIQUE (link)
);