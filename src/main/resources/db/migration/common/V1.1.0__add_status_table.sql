create table if not exists status
(
    name        varchar(25) not null,
    locale      varchar(11) not null,
    title       varchar(50) not null,
    description varchar(500)   not null,
    primary key (name, locale)
);