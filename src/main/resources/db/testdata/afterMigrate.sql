SET foreign_key_checks = 0;

TRUNCATE category;
TRUNCATE user;
TRUNCATE transaction;

SET foreign_key_checks = 1;

insert into category (description, color) values ('Saúde','#06C8F3');
insert into category (description, color) values ('Alimentação','#06F343');
insert into category (description, color) values ('Lazer','#EFF306');

insert into user (name, email, password, balance) values ('Maria Joaquina', 'maria.joaquina@budget.com', '123', 1000);
insert into user (name, email, password, balance) values ('Cirilo', 'cirilo@budget.com', '123', 1250);

insert into transaction (code, value, user_id, type, category_id, description, date, creation_date) values ('24faa01b-c97b-47a6-be67-b054bfe78d55', 100, 1, 'EXPENSE', 1, 'Compra do mês', curdate(), utc_timestamp);