create table if not exists order_status
(
    id          bigint      not null auto_increment,
    order_id    bigint      not null,
    status_id   bigint      not null,
    status_date datetime(6) not null,
    primary key (id),
    foreign key (status_id) references status (id),
    foreign key (order_id) references client_order (id)
);