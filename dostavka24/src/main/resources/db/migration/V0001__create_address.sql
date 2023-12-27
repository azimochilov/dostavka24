CREATE TABLE addresses (
                           id SERIAL PRIMARY KEY,
                           street VARCHAR(255),
                           city VARCHAR(255),
                           longitude DOUBLE PRECISION,
                           latitude DOUBLE PRECISION
);