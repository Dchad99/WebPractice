drop table orders;
CREATE TABLE orders
(
    id         serial PRIMARY KEY,
    clientId    INTEGER NOT NULL,
    productId INTEGER NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (clientId)
            REFERENCES users (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_product
        FOREIGN KEY (productId)
            REFERENCES products (id)
            ON DELETE CASCADE
);