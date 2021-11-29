CREATE TABLE products
(
    id    serial PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    price Integer        NOT NULL,
    date  TIMESTAMP          NOT NULL,
    description VARCHAR(250)
);

CREATE TABLE users
(
    id serial PRIMARY KEY,
    username  VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL,
    userHash VARCHAR(250) NOT NULL
);

insert into products(name, price, date, description) values ('Milk', 120 , '2021-01-01', 'Burionka');
insert into products(name, price, date, description) values ('Tomat', 420 , '2022-02-03', 'Red');
insert into products(name, price, date, description) values ('MeatChicken', 1220 , '2022-02-03', 'Chicken');
insert into products(name, price, date, description) values ('MeatGoat', 4310 , '2022-05-05', 'Goat');
insert into products(name, price, date, description) values ('MeatCow', 4310 , '2022-08-02', 'Cow');