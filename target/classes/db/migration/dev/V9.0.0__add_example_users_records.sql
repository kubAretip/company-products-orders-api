-- password for all users is 12345Aa@
insert into user (id, first_name, last_name, username, email, activated, password)
values (1000, 'James', 'Smith', 'marketer', 'james.smith@example.com', 1,
        '$2a$10$hI.lAD.4UsAmvofiFBWBeOERElM1r50A1lZTdQMZhZ5fOjwmLi3ce');
insert into user_authority (user_id, authority_name)
values (1000, 'ROLE_USER');
insert into user_authority (user_id, authority_name)
values (1000, 'ROLE_MARKETER');

insert into user (id, first_name, last_name, username, email, activated, password)
values (1001, 'Maria', 'Garcia', 'supervisor', 'maria.garcia@example.com', 1,
        '$2a$10$hI.lAD.4UsAmvofiFBWBeOERElM1r50A1lZTdQMZhZ5fOjwmLi3ce');
insert into user_authority (user_id, authority_name)
values (1001, 'ROLE_USER');
insert into user_authority (user_id, authority_name)
values (1001, 'ROLE_SUPERVISOR');

insert into user (id, first_name, last_name, username, email, activated, password)
values (1002, 'James', 'Johnson', 'storekeeper', 'james.johnson@example.com', 1,
        '$2a$10$hI.lAD.4UsAmvofiFBWBeOERElM1r50A1lZTdQMZhZ5fOjwmLi3ce');
insert into user_authority (user_id, authority_name)
values (1002, 'ROLE_USER');
insert into user_authority (user_id, authority_name)
values (1002, 'ROLE_STOREKEEPER');

insert into user (id, first_name, last_name, username, email, activated, password)
values (1003, 'Thomas', 'Brown', 'executor', 'thomas.brown@example.com', 1,
        '$2a$10$hI.lAD.4UsAmvofiFBWBeOERElM1r50A1lZTdQMZhZ5fOjwmLi3ce');
insert into user_authority (user_id, authority_name)
values (1003, 'ROLE_USER');
insert into user_authority (user_id, authority_name)
values (1003, 'ROLE_EXECUTOR');

insert into user (id, first_name, last_name, username, email, activated, password)
values (1004, 'Denzel', 'Washington', 'moderator', 'denzel.washington@example.com', 1,
        '$2a$10$hI.lAD.4UsAmvofiFBWBeOERElM1r50A1lZTdQMZhZ5fOjwmLi3ce');
insert into user_authority (user_id, authority_name)
values (1004, 'ROLE_USER');
insert into user_authority (user_id, authority_name)
values (1004, 'ROLE_MODERATOR');