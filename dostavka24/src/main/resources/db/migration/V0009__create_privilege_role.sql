CREATE TABLE privilege_role (
                                       id BIGSERIAL PRIMARY KEY,
                                       privilege_id BIGINT NOT NULL,
                                       role_id BIGINT NOT NULL,
                                       FOREIGN KEY (privilege_id) REFERENCES entity_table(id),
                                       FOREIGN KEY (role_id) REFERENCES role(id)
);