#create schema
CREATE SCHEMA `test_db` DEFAULT CHARACTER SET utf8;

#create user and grantall privileges
create user 'test_user'@'localhost' identified by 'test_user';
grant all privileges on test_db.* to 'test_user'@'localhost';

#create table
create table tbl_test_domain( 
	id int auto_increment primary key,
	name varchar(150)
);
