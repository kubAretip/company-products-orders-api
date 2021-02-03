create table if not exists phone_country_code
(
    country varchar(64) not null unique,
    code    varchar(7)  not null unique,
    primary key (code)
);