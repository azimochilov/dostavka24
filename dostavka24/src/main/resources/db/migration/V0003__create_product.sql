CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) UNIQUE NOT NULL,
                         price INTEGER NOT NULL,
                         create_at TIMESTAMP,
                         product_status VARCHAR(255)
);