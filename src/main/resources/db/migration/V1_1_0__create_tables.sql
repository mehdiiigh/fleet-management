create table Fleet_Management_DB.company
(
    company_id   bigint auto_increment
        primary key,
    created_at   timestamp default CURRENT_TIMESTAMP null,
    company_name varchar(255)                        not null
);

create table Fleet_Management_DB.employee
(
    employee_id bigint auto_increment
        primary key,
    company_id  bigint null,
    constraint fk_employee_company
        foreign key (company_id) references Fleet_Management_DB.company (company_id)
);

create table Fleet_Management_DB.vehicle
(
    vehicle_type     varchar(255)                        not null
        primary key,
    company_id       bigint                              null,
    created_at       timestamp default CURRENT_TIMESTAMP null,
    emission_factors double                              null,
    constraint fk_vehicle_company
        foreign key (company_id) references Fleet_Management_DB.company (company_id)
);

create table Fleet_Management_DB.vehicle_usage
(
    id                     bigint auto_increment
        primary key,
    average_weekly_mileage double       not null,
    employee_id            bigint       null,
    vehicle_type           varchar(255) null,
    constraint fk_vehicle_usage_vehicle
        foreign key (vehicle_type) references Fleet_Management_DB.vehicle (vehicle_type),
    constraint fk_vehicle_usage_employee
        foreign key (employee_id) references Fleet_Management_DB.employee (employee_id)
);



