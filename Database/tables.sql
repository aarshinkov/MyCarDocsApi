CREATE TABLE IF NOT EXISTS users(
	user_id varchar(200) not null primary key,
	email varchar(100) not null unique,
	password varchar(100) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	created_on timestamp not null default NOW(),
	edited_on timestamp,
	reset_pass_code text
);

CREATE TABLE IF NOT EXISTS roles(
	role varchar(40) not null primary key
);

INSERT INTO roles VALUES ('ADMIN');
INSERT INTO roles VALUES ('USER');

CREATE TABLE IF NOT EXISTS user_roles(
	user_id varchar(200) not null references users(user_id),
	role varchar(40) not null references roles(role),	
	CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role)
);

CREATE TABLE IF NOT EXISTS cars(
	car_id varchar(200) not null primary key,
	brand varchar(100) not null,
	model varchar(100) not null,
	color varchar(50) not null,
	transmission int not null default 0,
	power_type int not null,
	year int not null,
	license_plate varchar(12) not null,
	alias varchar(100),
	owner varchar(200) not null references users(user_id) on update cascade on delete cascade,
	added_on timestamp not null default NOW(),
	edited_on timestamp
);

CREATE TABLE IF NOT EXISTS policies(
	policy_id varchar(200) not null primary key,
	number varchar(100) not null,
	type int not null,
	ins_name varchar(100) not null,
	car_id varchar(200) not null references cars(car_id) on update cascade on delete cascade,
	start_date timestamp not null,
	end_date timestamp not null
);

CREATE TABLE IF NOT EXISTS fuel_expenses(
	fuel_expense_id varchar(200) not null primary key,
	price_per_litre numeric not null,
	litres numeric not null,
	discount numeric,
	mileage bigint,
	car_id varchar(200) not null references cars(car_id) on update cascade on delete cascade,
	created_on timestamp not null default NOW(),
	edited_on timestamp
);

CREATE TABLE IF NOT EXISTS service_expense_types(
	type int not null primary key,
	type_description varchar(200) not null
);

INSERT INTO service_expense_types VALUES (1, 'Scheduled maintenance');
INSERT INTO service_expense_types VALUES (2, 'Engine repair');
INSERT INTO service_expense_types VALUES (3, 'Tyres change');
INSERT INTO service_expense_types VALUES (4, 'Oil change');
INSERT INTO service_expense_types VALUES (5, 'Oil filter change');
INSERT INTO service_expense_types VALUES (6, 'Air filter change');
INSERT INTO service_expense_types VALUES (7, 'Battery replacement');
INSERT INTO service_expense_types VALUES (8, 'Wheels balance');
INSERT INTO service_expense_types VALUES (9, 'Belt replacement');
INSERT INTO service_expense_types VALUES (10, 'Tow');
INSERT INTO service_expense_types VALUES (11, 'Lights replacement');
INSERT INTO service_expense_types VALUES (12, 'Part/s change');

CREATE TABLE IF NOT EXISTS service_expenses(
	service_expense_id varchar(200) not null primary key,
	type int not null default 1 references service_expense_types(type) on delete restrict,
	car_id varchar(200) not null references cars(car_id) on update cascade on delete cascade,
	price numeric not null,
	mileage bigint,
	notes text,
	created_on timestamp not null default NOW(),
	edited_on timestamp
);

CREATE TABLE IF NOT EXISTS sys_params(
	name varchar(300) not null primary key,
	value varchar(2000) not null,
	descr varchar(2000)
);

INSERT INTO sys_params VALUES ('MAIL.HOST', 'smtp.gmail.com', null);
INSERT INTO sys_params VALUES ('MAIL.PORT', '587', null);
INSERT INTO sys_params VALUES ('MAIL.PROTOCOL', 'smtp', null);
INSERT INTO sys_params VALUES ('MAIL.SENDER', 'mycardocsapp@gmail.com', null);
INSERT INTO sys_params VALUES ('MAIL.USERNAME', 'mycardocsapp@gmail.com', null);
INSERT INTO sys_params VALUES ('MAIL.PASSWORD', 'ThisIsLynx@9712', null);

-- Mail sequence
CREATE SEQUENCE IF NOT EXISTS public.s_mails
	INCREMENT 1
	START 1;
	
ALTER SEQUENCE public.s_mails	
	OWNER TO bss_user;
	
CREATE TABLE IF NOT EXISTS mailbox(
	mail_id int not null primary key default nextval('s_mails'),
	sender varchar(250) not null,
	receivers varchar(2000) not null,
	subject varchar(300) not null,
	content text not null,
	is_sent boolean not null default false
);