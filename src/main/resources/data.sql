INSERT INTO authority_table
VALUES (1, 'ROLE_ADMIN');

INSERT INTO authority_table
VALUES (2, 'ROLE_USER');

INSERT INTO user_table (user_id, first_name, last_name, username, email, password)
VALUES (101, 'Abdul', 'Kamal', 'Abdul-Kamal-001', 'abdulkamal@gmail.com', 'Abdul-Kamal-001');

INSERT INTO user_table (user_id, first_name, last_name, username, email, password)
VALUES (102, 'Abdul', 'Karim', 'Karim-002', 'abdulkarim@gmail.com', 'Karim-002');

INSERT INTO user_authority (user_id, authority_id)
VALUES (101, 1);

INSERT INTO user_authority (user_id, authority_id)
VALUES (101, 2);

INSERT INTO user_authority (user_id, authority_id)
VALUES (102, 2);