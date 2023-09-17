create table Fleet_Management_DB.app_role
(
    id          int          not null
        primary key,
    description varchar(255) null,
    role_name   varchar(255) null
);

create table Fleet_Management_DB.app_user
(
    id       int          not null
        primary key,
    password varchar(255) null,
    username varchar(255) null,
    constraint uk_username
        unique (username)
);

create table Fleet_Management_DB.user_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint fk_user_role_user
        foreign key (user_id) references Fleet_Management_DB.app_user (id),
    constraint fk_user_role_role
        foreign key (role_id) references Fleet_Management_DB.app_role (id)
);