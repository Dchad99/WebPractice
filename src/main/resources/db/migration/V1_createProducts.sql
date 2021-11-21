CREATE TABLE products
(
    id    serial PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    price VARCHAR(50)        NOT NULL,
    date  TIMESTAMP          NOT NULL
);