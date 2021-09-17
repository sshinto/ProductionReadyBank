DROP TABLE IF EXISTS customer cascade;
CREATE TABLE customer (
customer_id INT AUTO_INCREMENT  PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
dob DATE NOT NULL,
address1 VARCHAR(50) NOT NULL,
address2 VARCHAR(50) NOT NULL,
city VARCHAR(50) NOT NULL,
contact_number VARCHAR(50) NOT NULL,
created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_timestamp TIMESTAMP
);

DROP TABLE IF EXISTS account cascade;
CREATE TABLE account (
account_number INT AUTO_INCREMENT  PRIMARY KEY,
customer_id INT NOT NULL,
account_type VARCHAR(50)  NOT NULL,
created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_timestamp TIMESTAMP,
foreign key (customer_id) references customer(customer_id)
);

DROP TABLE IF EXISTS Transaction cascade;
CREATE TABLE Transaction (
transaction_id INT AUTO_INCREMENT  PRIMARY KEY,
account_number INT NOT NULL,
description VARCHAR(100) NOT NULL,
transaction_type VARCHAR(50) NOT NULL,
amount double NOT NULL,
transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_timestamp TIMESTAMP,
foreign key (account_number) references account(account_number)
);
