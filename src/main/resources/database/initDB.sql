CREATE TABLE crudtest.users(
  id INT(8) PRIMARY KEY NOT NULL,
  name VARCHAR(25) NOT NULL,
  age INT NOT NULL,
  isAdmin BIT NOT NULL,
  createdDate TIMESTAMP NOT NULL);