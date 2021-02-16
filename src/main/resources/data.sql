
-- USER
-- hashed password: letmein
INSERT INTO security_user (id, username, password, first_name, last_name) VALUES
(1,  'admin', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Administrator', 'Administrator'),
(2,  'user1', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'User1', 'lastname'),
(3,  'user2', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'User2', 'lastname'),
(4,  'user3', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'User3', 'lastname');

-- ROLES

INSERT INTO security_role (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO security_role (id, role_name, description) VALUES (2, 'ROLE_USER', 'Rate API user');

INSERT INTO user_role(user_id, role_id) VALUES
 (1, 1), -- give admin ROLE_ADMIN
 (2, 2),  -- give user1 ROLE_USER
 (3, 2),  -- give user2 ROLE_USER
 (4, 1),  -- give user3 ROLE_USER
 (4, 2);  -- give user3 ROLE_USER
