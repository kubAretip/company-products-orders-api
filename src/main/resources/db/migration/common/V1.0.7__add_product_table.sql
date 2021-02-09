create table if not exists product
(
    id          bigint       not null auto_increment,
    name        varchar(150) not null,
    deleted     boolean      not null default false,
    category_id bigint       not null,
    unit_id     bigint       not null,
    foreign key (category_id) references category (id),
    foreign key (unit_id) references unit (id),
    primary key (id)
)