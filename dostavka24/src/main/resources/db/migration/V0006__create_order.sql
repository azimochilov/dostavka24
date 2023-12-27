CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id BIGINT,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        is_cart BOOLEAN DEFAULT TRUE,
                        phone VARCHAR(255),
                        total_price DOUBLE PRECISION,
                        status VARCHAR(255),
                        amount_of_products INTEGER,
                        delivery_time VARCHAR(255),
                        address_id BIGINT,
                        restaurant_id BIGINT,
                        CONSTRAINT fk_user_order FOREIGN KEY (user_id) REFERENCES dastavka24_user(id),
                        CONSTRAINT fk_address_order FOREIGN KEY (address_id) REFERENCES addresses(id),
                        CONSTRAINT fk_restaurant_order FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);