ALTER TABLE role
    ADD COLUMN privilege_id BIGINT;

ALTER TABLE role
    ADD CONSTRAINT fk_role_privilege
        FOREIGN KEY (privilege_id) REFERENCES privilege(id);
