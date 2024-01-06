
ALTER TABLE entity_table RENAME TO privilege;

ALTER TABLE privilege
    ADD COLUMN role_id BIGINT;

ALTER TABLE privilege
    ADD CONSTRAINT fk_role_privilege
        FOREIGN KEY (role_id) REFERENCES privilege_role(id);

CREATE TABLE role_privilege_table (
                                      id BIGSERIAL PRIMARY KEY,
                                      privilege_id BIGINT,
                                      role_id BIGINT,
                                      FOREIGN KEY (privilege_id) REFERENCES privilege(id),
                                      FOREIGN KEY (role_id) REFERENCES role(id)
);