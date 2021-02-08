create table if not exists category
(
    id      bigint       not null auto_increment,
    name    varchar(150) not null,
    deleted boolean      not null,
    primary key (id)
);