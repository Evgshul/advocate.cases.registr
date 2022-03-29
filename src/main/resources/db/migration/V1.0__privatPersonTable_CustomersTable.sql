CREATE TABLE IF NOT EXISTS persons(
 person_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
 fullname varchar(50),
 identifier varchar(50),
 phone varchar(50),
 email varchar(50)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS customers(
 customer_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
 customer_identifier varchar(50),
 customer_name varchar(50),
 declared_address varchar(50),
 s varchar(50),
 phone varchar(50),
 email varchar(50),
 person_id int,
 CONSTRAINT fk_customer_person_id
 FOREIGN KEY (person_id)
 REFERENCES persons(person_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;