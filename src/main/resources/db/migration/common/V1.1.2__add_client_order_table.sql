create table if not exists client_order
(
    id                     bigint not null auto_increment,
    client_id              bigint not null,
    marketer_user_id       bigint not null,
    supervisor_user_id     bigint,
    delivery_address_id    bigint not null,
    additional_information varchar(2000),
    primary key (id),
    foreign key (client_id) references client (id),
    foreign key (marketer_user_id) references user (id),
    foreign key (supervisor_user_id) references user (id),
    foreign key (delivery_address_id) references address (id)
);