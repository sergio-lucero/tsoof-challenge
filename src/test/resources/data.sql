-- insert company

INSERT INTO challenge.company(id, cuit, company_name, created_at, updated_at)
VALUES ('421dd326-244b-48ea-b3d2-96ca3fa437a7', '30504018845', 'VOLKSWAGEN ARGENTINA S A', '2025-03-01 13:23:37', '2025-03-01 13:23:37');

INSERT INTO challenge.company(id, cuit, company_name, created_at, updated_at)
VALUES ('ef7b9099-330b-4127-b8e0-101ab9d05d6e', '33679139369', 'TOYOTA ARGENTINA S A', '2025-03-01 13:23:37', '2025-03-01 13:23:37');

INSERT INTO challenge.company(id, cuit, company_name, created_at, updated_at)
VALUES ('e5adf366-5e4a-471f-b819-bf58eb5eb394', '30678519681', 'FORD ARGENTINA SOCIEDAD EN COMANDITA POR ACCIONES', '2025-03-01 13:23:37', '2025-03-01 13:23:37');

INSERT INTO challenge.company(id, cuit, company_name, created_at, updated_at)
VALUES ('119c6763-b33d-4426-98ad-9562a1b260a0', '30662071680', 'GENERAL MOTORS DE ARGENTINA SOCIEDAD DE RESPONSABILIDAD LIMI', '2025-02-01 13:23:37', '2025-03-01 13:23:37');

INSERT INTO challenge.company(id, cuit, company_name, created_at, updated_at)
VALUES ('b37fbfb9-0a7e-4624-834a-5ab0712d8073', '30503317814', 'RENAULT ARGENTINA S.A', '2025-02-01 13:23:37', '2025-03-01 13:23:37');

INSERT INTO challenge.company(id, cuit, company_name, created_at, updated_at, deleted_at)
VALUES ('a6da2485-de7e-4e7a-a1a7-65b0dde702b6', '30504744538', 'PEUGEOT CITROEN ARGENTINA SOCIEDAD ANONIMA', '2025-02-01 13:23:37', '2025-03-01 13:23:37', '2025-03-05 13:23:37');

-- insert transfer
-- company 421dd326-244b-48ea-b3d2-96ca3fa437a7
INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('6bfeae05-397b-45a7-99b1-56a53f0da58e', 1500000.50, '421dd326-244b-48ea-b3d2-96ca3fa437a7', '1234567890123456', null, '2025-03-01 14:23:37', '2025-03-01 14:23:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('cc97fa6f-65cf-4740-a7b6-6602c7f83fa9', 2500000.50, '421dd326-244b-48ea-b3d2-96ca3fa437a7', '1234567890123456', null, '2025-03-01 14:30:37', '2025-03-01 14:30:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('6bc0edcc-7f07-4547-9fc8-65a9b639c90a', 1600000.75, '421dd326-244b-48ea-b3d2-96ca3fa437a7', '1234567890123456', null, '2025-03-08 14:35:37', '2025-03-08 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('4612c92a-aa68-4b28-b98f-ee91223d9d67', 1605000.75, '421dd326-244b-48ea-b3d2-96ca3fa437a7', '1234567890123456', null, '2025-03-09 14:50:37', '2025-03-09 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('370abca8-b2ae-490e-897c-90631650176a', 2605000.75, '421dd326-244b-48ea-b3d2-96ca3fa437a7', '1234567890123456', null, '2025-03-10 14:50:37', '2025-03-10 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('8b58d97b-9074-487a-833c-07d712a4f774', 3500000.75, '421dd326-244b-48ea-b3d2-96ca3fa437a7', null , '567890123456789', '2025-03-10 14:50:37', '2025-03-10 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('79fe2717-aec1-498f-92d4-148a56c35c3e', 13500000.75, '421dd326-244b-48ea-b3d2-96ca3fa437a7', null , '567890123456789', '2025-03-09 14:50:37', '2025-03-09 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('f89c7796-8fc7-42c0-ac17-cce2d1c002ef', 15500000.75, '421dd326-244b-48ea-b3d2-96ca3fa437a7', null , '567890123456789', '2025-03-12 14:50:37', '2025-03-12 14:35:37' );


-- company ef7b9099-330b-4127-b8e0-101ab9d05d6e
INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('73d5b746-6235-496d-b19a-1d8abd253b18', 1800000.50, 'ef7b9099-330b-4127-b8e0-101ab9d05d6e', '1234567890123456', null, '2025-03-01 14:23:37', '2025-03-01 14:23:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('2d3c681a-5f9a-4452-815a-00ade88d06a5', 2300000.50, 'ef7b9099-330b-4127-b8e0-101ab9d05d6e', '1234567890123456', null, '2025-03-01 14:30:37', '2025-03-01 14:30:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('9559ad3a-6606-409f-a746-68c98db23378', 165000.75, 'ef7b9099-330b-4127-b8e0-101ab9d05d6e', '1234567890123456', null, '2025-03-08 14:35:37', '2025-03-08 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('191ed255-a01b-42ff-80f1-a587de81b3ec', 1205000.75, 'ef7b9099-330b-4127-b8e0-101ab9d05d6e', '1234567890123456', null, '2025-03-09 14:50:37', '2025-03-09 14:35:37' );


-- company e5adf366-5e4a-471f-b819-bf58eb5eb394
INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('7f7dcbdc-00bb-490f-8304-b66266827090', 1800000.50, 'e5adf366-5e4a-471f-b819-bf58eb5eb394', '1234567890123456', null, '2025-01-01 14:23:37', '2025-01-01 14:23:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('bd057684-5eeb-4089-b6bc-3bd4cdf3a7cf', 2300000.50, 'e5adf366-5e4a-471f-b819-bf58eb5eb394', '1234567890123456', null, '2025-01-01 14:30:37', '2025-01-01 14:30:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('0c1696b0-9380-4986-ad98-39bbdce78e4b', 165000.75, 'e5adf366-5e4a-471f-b819-bf58eb5eb394', '1234567890123456', null, '2025-01-08 14:35:37', '2025-01-08 14:35:37' );

INSERT INTO challenge.transfer(id, amount, company_id, debit_account, credit_account, created_at, updated_at)
VALUES('565babde-ea9e-4f28-b875-780f07c24d67', 1205000.75, 'e5adf366-5e4a-471f-b819-bf58eb5eb394', '1234567890123456', null, '2025-01-09 14:50:37', '2025-01-09 14:35:37' );