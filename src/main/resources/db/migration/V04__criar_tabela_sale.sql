CREATE TABLE sale (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	description VARCHAR(50) NOT NULL,
	due_date DATETIME NOT NULL,
	pay_date DATETIME,
	price DECIMAL(10,2) NOT NULL,
	obs VARCHAR(100),
	type VARCHAR(20) NOT NULL,
	id_category BIGINT(20) NOT NULL REFERENCES category(id),
	id_person BIGINT(20) NOT NULL REFERENCES person(id)
);

INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'INCOME', 1, 1);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'DEBIT', 2, 2);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Top Club', '2017-06-10', null, 120, null, 'INCOME', 3, 3);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'INCOME', 3, 4);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('DMAE', '2017-06-10', null, 200.30, null, 'DEBIT', 3, 5);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'INCOME', 4, 6);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Bahamas', '2017-06-10', null, 500, null, 'INCOME', 1, 7);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'DEBIT', 4, 8);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'DEBIT', 3, 9);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'INCOME', 5, 10);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Café', '2017-06-10', null, 8.32, null, 'DEBIT', 1, 5);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'DEBIT', 5, 4);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'DEBIT', 4, 3);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'DEBIT', 4, 2);
INSERT INTO sale (description, due_date, pay_date, price, obs, type, id_category, id_person) values ('Lanche', '2017-06-10', null, 10.20, null, 'DEBIT', 4, 1);