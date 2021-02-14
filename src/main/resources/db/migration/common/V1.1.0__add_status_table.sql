create table if not exists status
(
    id          bigint       not null auto_increment,
    name        varchar(25)  not null,
    locale      varchar(11)  not null,
    title       varchar(50)  not null,
    description varchar(500) not null,
    primary key (id),
    constraint unique (name, locale)
);