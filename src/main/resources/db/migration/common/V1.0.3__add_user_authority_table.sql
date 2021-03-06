create table if not exists user_authority
(
    user_id        bigint      not null,
    authority_name varchar(50) not null,
    primary key (user_id, authority_name),
    foreign key (user_id) references user (id),
    foreign key (authority_name) references authority (name)
);