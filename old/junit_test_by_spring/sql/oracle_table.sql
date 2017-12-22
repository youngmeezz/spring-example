-- Create User
create user test_user identified by test_user
grant resource, connect to test_user

-- TABLE FOR TestDomain
create table tbl_test_domain (
	id number primary key,
	name varchar2(150)
);
create sequence test_domain_id_seq;

