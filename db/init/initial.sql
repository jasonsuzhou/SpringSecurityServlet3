INSERT INTO users(username,password,enabled) VALUES ('jason','jason', TRUE);
INSERT INTO users(username,password,enabled) VALUES ('minhua','minhua', TRUE);
INSERT INTO user_roles (username, role) VALUES ('minhua', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('minhua', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('jason', 'ROLE_USER');