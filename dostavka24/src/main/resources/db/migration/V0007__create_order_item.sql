CREATE TABLE order_item (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255),
                            count INTEGER,
                            created_at TIMESTAMP,
                            updated_at TIMESTAMP,
                            product_id BIGINT,
                            order_id BIGINT,
                            CONSTRAINT fk_product_order_item FOREIGN KEY (product_id) REFERENCES product(id),
                            CONSTRAINT fk_order_order_item FOREIGN KEY (order_id) REFERENCES orders(id)
);