SET foreign_key_checks = 0;

TRUNCATE category;
TRUNCATE user;
TRUNCATE transaction;
TRUNCATE permission;
TRUNCATE profile;
TRUNCATE profile_permission;

SET foreign_key_checks = 1;


insert into user (name, email, password, balance) values ('Maria Joaquina', 'maria.joaquina@budget.com', '123', 1000);
insert into user (name, email, password, balance) values ('Cirilo', 'cirilo@budget.com', '123', 1250);

insert into category (description, color, user_id) values ('Saúde','#06C8F3', 1);
insert into category (description, color, user_id) values ('Alimentação','#06F343', 1);
insert into category (description, color, user_id) values ('Lazer','#EFF306', 2);

insert into transaction (code, value, user_id, type, category_id, description, date, creation_date) values ('24faa01b-c97b-47a6-be67-b054bfe78d55', 150, 1, 'EXPENSE', 1, 'Remédios para gripe', curdate(), utc_timestamp);
insert into transaction (code, value, user_id, type, category_id, description, date, creation_date) values ('24faa01b-c97b-47a6-be67-b054bfe78d55', 65, 2, 'EXPENSE', 2, 'Noite da pizza', curdate(), utc_timestamp);

insert into permission (id, name, description) values (1, 'CHANGE_USERS_PROFILES_PERMISSIONS', 'Allows creation, changing and deleting of users');
insert into permission (id, name, description) values (2, 'CHANGE_CATEGORIES', 'Allows creation, changing and deleting of categories');
insert into permission (id, name, description) values (3, 'CHANGE_TRANSACTIONS', 'Allows creation, changing and deleting of transactions');
insert into permission (id, name, description) values (4, 'CONSULT_USERS_PROFILES_PERMISSIONS', 'Allows to consult users');
insert into permission (id, name, description) values (5, 'CONSULT_TRANSATIONS', 'Allows to consult transactions');
insert into permission (id, name, description) values (6, 'CONSULT_CATEGORIES', 'Allows to consult categories');
insert into permission (id, name, description) values (7, 'CONSULT_REPORTS', 'Allows to consult reports');

insert into profile (id, name) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretário');

insert into profile_permission (profile_id, permission_id) select 1, id from permission;
insert into profile_permission (profile_id, permission_id) select 2, id from permission where name like 'CONSULT_%';
insert into profile_permission (profile_id, permission_id) select 3, id from permission where name like 'CONSULT_%';