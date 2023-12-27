CREATE TABLE restaurants (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             address_id BIGINT,
                             CONSTRAINT fk_address_restaurant FOREIGN KEY (address_id) REFERENCES addresses(id)
);