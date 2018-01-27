-- spring schema
CREATE SCHEMA `spring` DEFAULT CHARACTER SET utf8;
CREATE USER 'spring'@'localhost' identified by 'spring';
grant all privileges on spring.* to 'spring'@'localhost';


-- testdb schema
CREATE SCHEMA `testdb` DEFAULT CHARACTER SET utf8;
grant all privileges on testdb.* to 'spring'@'localhost';