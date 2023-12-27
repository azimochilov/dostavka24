CREATE TABLE dastavka24_user (
                                 id SERIAL PRIMARY KEY,
                                 userName VARCHAR(255),
                                 password VARCHAR(255),
                                 firstName VARCHAR(255),
                                 lastName VARCHAR(255),
                                 email VARCHAR(255),
                                 updatedAt TIMESTAMP,
                                 isActive BOOLEAN DEFAULT false,
                                 registeredAt TIMESTAMP,
                                 address_id BIGINT,
                                 FOREIGN KEY (address_id) REFERENCES addresses(id)
);