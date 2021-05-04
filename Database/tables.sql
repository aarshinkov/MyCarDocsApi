CREATE TABLE IF NOT EXISTS users(
	user_id varchar(200) not null primary key,
	email varchar(100) not null unique,
	password varchar(100) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	created_on timestamp not null default NOW(),
	edited_on timestamp
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
	user_id varchar(200) not null references users(user_id) on update cascade on delete cascade,
	created_on timestamp not null default NOW(),
	edited_on timestamp
);

