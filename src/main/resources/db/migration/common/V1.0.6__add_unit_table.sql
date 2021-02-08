create table if not exists unit
(
    id      bigint      not null auto_increment,
    name    varchar(35) not null,
    symbol  varchar(10) not null,
    deleted boolean     not null default 0,
    primary key (id)
)