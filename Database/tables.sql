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