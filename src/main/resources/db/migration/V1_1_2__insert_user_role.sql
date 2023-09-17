SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO app_role (id, role_name, description)
VALUES (1, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- password test1234
INSERT INTO app_user (id, password, username)
VALUES (1, '$2a$10$5AWyzymSnNypg9BkMOyKE.zA05GtRKHCoWimh.q2w.KAO5koBYPM6', 'admin');

TRUNCATE TABLE user_role;
INSERT INTO user_role(user_id, role_id)
VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
