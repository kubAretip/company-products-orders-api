create table if not exists country_calling_code
(
    country varchar(64) not null unique,
    code    varchar(7)  not null,
    primary key (country)
);