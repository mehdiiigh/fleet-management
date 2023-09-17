SET FOREIGN_KEY_CHECKS = 0;

insert into company (company_id, company_name)
values (1, 'Company1');

insert into company (company_id, company_name)
values (2, 'Company2');

insert into company (company_id, company_name)
values (3, 'Company3');


insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType1', 1, 1.2);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType2', 1, 5.2);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType3', 1, 3.8);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType4', 2, 1.9);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType5', 2, 3.2);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType6', 2, 2.3);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType7', 3, 1.7);

insert into vehicle (vehicle_type, company_id, emission_factors)
values ('VehicleType8', 3, 9.2);

insert into employee (employee_id, company_id)
values (1, 1);

insert into employee (employee_id, company_id)
values (2, 2);

insert into employee (employee_id, company_id)
values (3, 3);

insert into employee (employee_id, company_id)
values (4, 2);

insert into employee (employee_id, company_id)
values (5, 3);

insert into employee (employee_id, company_id)
values (6, 1);

insert into employee (employee_id, company_id)
values (7, 3);

insert into employee (employee_id, company_id)
values (8, 2);

insert into employee (employee_id, company_id)
values (9, 1);

SET FOREIGN_KEY_CHECKS = 1;
