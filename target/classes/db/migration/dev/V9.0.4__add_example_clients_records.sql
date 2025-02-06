INSERT INTO cpo.client (id, company_name, first_name, last_name, email, phone_number)
VALUES (1, 'CompXYZ1', 'Mary', 'Miller', 'compxyz1@example.com', '+12025550131');
INSERT INTO cpo.address (id, country, street, zip_code, building, city, client_id)
VALUES (1, 'USA', 'Jennings Ave. Brooklyn', 'NY 11105', '8240', 'New York', 1);

INSERT INTO cpo.client (id, company_name, first_name, last_name, email, phone_number)
VALUES (2, 'ABComp123', 'Linda', 'Evans', 'abcomp123@example.com', '+441632960305');
INSERT INTO cpo.address (id, country, street, zip_code, building, city, client_id)
VALUES (2, 'England', 'South Street', 'N75 2QN', '14', 'London', 2);

INSERT INTO cpo.client (id, company_name, first_name, last_name, email, phone_number)
VALUES (3, 'Corp999', 'David', 'Martin', 'corp999@example.com', '+61491570156');
INSERT INTO cpo.address (id, country, street, zip_code, apartment, building, city, client_id)
VALUES (3, 'Australia', 'Hunter St', 'NSW 2000', '3', '19', 'Sydney', 3);