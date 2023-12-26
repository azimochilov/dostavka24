CREATE TABLE dostavka24_user (
                                 id SERIAL PRIMARY KEY,
                                 user_name VARCHAR(255) NOT NULL,
                                 password VARCHAR(255) NOT NULL,
                                 first_name VARCHAR(255),
                                 last_name VARCHAR(255),
                                 email VARCHAR(255),
                                 updated_at TIMESTAMP,
                                 is_active BOOLEAN DEFAULT FALSE,
                                 registered_at TIMESTAMP,
                                 address_id BIGINT,
                                 CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id)
);
