create table if not exists client
(
    id           bigint       not null auto_increment,
    company_name varchar(50)  not null unique,
    first_name   varchar(50)  not null,
    last_name    varchar(50)  not null,
    email        varchar(100) not null unique,
    phone_number varchar(20)  not null unique,
    primary key (id)
);