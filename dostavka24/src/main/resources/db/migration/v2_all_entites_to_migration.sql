CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) UNIQUE NOT NULL
);
-- Create Restaurant Table
CREATE TABLE restaurants (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             address_id BIGINT,
                             CONSTRAINT fk_address_restaurant FOREIGN KEY (address_id) REFERENCES addresses(id)
);
-- Create Product Table
CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) UNIQUE NOT NULL,
                         price INTEGER NOT NULL,
                         create_at TIMESTAMP,
                         product_status VARCHAR(255)
);
-- Create Order Table
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
                        CONSTRAINT fk_user_order FOREIGN KEY (user_id) REFERENCES dostavka24_user(id),
                        CONSTRAINT fk_address_order FOREIGN KEY (address_id) REFERENCES addresses(id),
                        CONSTRAINT fk_restaurant_order FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- Create OrderItem Table
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
-- Create Address Table
CREATE TABLE addresses (
                           id SERIAL PRIMARY KEY,
                           street VARCHAR(255),
                           city VARCHAR(255),
                           longitude DOUBLE PRECISION,
                           latitude DOUBLE PRECISION
);
