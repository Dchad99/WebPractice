CREATE TABLE orders
(
    id         serial PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_product
            FOREIGN KEY (id)
            REFERENCES products (id)
            ON DELETE CASCADE
);