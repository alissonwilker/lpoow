CREATE TABLE Employee (
  id int NOT NULL,
  name varchar(20) DEFAULT NULL,
  role varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO Employee (id, name, role)
VALUES
	(3, 'Pankaj', 'CEO');
INSERT INTO Employee (id, name, role)
VALUES
	(14, 'David', 'Developer');
