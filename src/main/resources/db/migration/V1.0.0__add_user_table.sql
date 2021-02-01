create table if not exists user
(
    id             bigint              not null auto_increment,
    username       varchar(100)        not null unique,
    password       varchar(60)         not null,
    first_name     varchar(50),
    last_name      varchar(50),
    email          varchar(100) unique not null,
    phone_number   varchar(15),
    activated      boolean             not null default 0,
    activation_key varchar(124),
    primary key (id)
);