create table if not exists authority
(
    name      varchar(50) not null unique,
    view_name varchar(45) not null,
    primary key (name)
);