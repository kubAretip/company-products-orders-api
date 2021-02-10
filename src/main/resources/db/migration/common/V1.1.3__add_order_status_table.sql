create table if not exists order_status
(
    id            bigint      not null auto_increment,
    order_id      bigint      not null,
    status        varchar(25) not null,
    status_locale varchar(11) not null,
    status_date   datetime(6) not null,
    primary key (id),
    foreign key (status, status_locale) references status (name, locale),
    foreign key (order_id) references client_order (id)
);