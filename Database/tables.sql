-- Users sequence
CREATE SEQUENCE IF NOT EXISTS public.s_users
    INCREMENT 1
    START 1;

ALTER SEQUENCE public.s_users
    OWNER TO mcd_user;

CREATE TABLE IF NOT EXISTS users(
	user_id int not null primary key default nextval('s_users'),
	email varchar(100) not null unique,
	password varchar(100) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	edited_on timestamp
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
	owner int not null references users(user_id) on update cascade on delete cascade,
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