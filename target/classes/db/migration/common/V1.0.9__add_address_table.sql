create table if not exists address
(
    id        bigint      not null auto_increment,
    country   varchar(64) not null,
    street    varchar(50) not null,
    zip_code  varchar(20) not null,
    apartment varchar(20),
    building  varchar(20) not null,
    city      varchar(50) not null,
    client_id bigint,
    primary key (id),
    foreign key (client_id) references client (id)
)