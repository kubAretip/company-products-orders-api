create table if not exists user_activation_report
(
    id      bigint       not null auto_increment,
    name    varchar(100) not null,
    type    varchar(30)  not null,
    user_id bigint       not null,
    data    longblob     not null,
    primary key (id),
    foreign key (user_id) references user (id)
);