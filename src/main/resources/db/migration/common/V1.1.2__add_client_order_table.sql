create table if not exists client_order
(
    id                  bigint not null auto_increment,
    client_id           bigint not null,
    created_by_user_id  bigint not null,
    accepted_by_user_id bigint,
    primary key (id),
    foreign key (client_id) references client (id),
    foreign key (created_by_user_id) references user (id),
    foreign key (accepted_by_user_id) references user (id)
);