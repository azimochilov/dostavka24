ALTER TABLE dastavka24_user
    ADD COLUMN role_id BIGINT;

ALTER TABLE dastavka24_user
    ADD CONSTRAINT fk_user_role
        FOREIGN KEY (role_id) REFERENCES role(id);