create table if not exists order_product
(
    id               bigint not null auto_increment,
    order_id         bigint not null,
    product_id       bigint not null,
    quantity         int    not null,
    executor_user_id bigint,
    primary key (id),
    foreign key (order_id) references client_order (id),
    foreign key (product_id) references product (id),
    foreign key (executor_user_id) references user (id)
)