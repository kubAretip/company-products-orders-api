create table if not exists user
(
    id             bigint       not null auto_increment,
    username       varchar(100) not null unique,
    password       varchar(60),
    first_name     varchar(50)  not null,
    last_name      varchar(50)  not null,
    email          varchar(100) not null unique,
    phone_number   varchar(20),
    activated      boolean      not null default 0,
    activation_key varchar(60),
    primary key (id)
);