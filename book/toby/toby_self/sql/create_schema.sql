
#spring schema 持失
CREATE SCHEMA `spring` DEFAULT CHARACTER SET utf8;
grant all privileges on spring.* to 'spring'@'localhost';

#testdb schema 持失
CREATE SCHEMA `testdb` DEFAULT CHARACTER SET utf8;
grant all privileges on testdb.* to 'spring'@'localhost';