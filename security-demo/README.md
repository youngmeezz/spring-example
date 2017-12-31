# Spring Security Demo

#### index

<a href="#database">Database</a>


---

#### Database

> CREATE SCHEMA & USER

```
CREATE SCHEMA `spring_demo` DEFAULT CHARACTER SET utf8;

CREATE USER 'spring_user'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON spring_demo.* to 'spring_user'@'localhost';
```

> Create table

```
-- member
CREATE TABLE tbl_members (
  login_id varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  primary key(login_id)
);

-- member roles
CREATE TABLE tbl_member_roles (
  id bigint not null auto_increment,
  role_name varchar(255),
  login_id varchar(255),
  primary key(id),
  foreign key(login_id) references tbl_members(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- board
CREATE TABLE tbl_boards (
  id bigint not null auto_increment,
  title varchar(255),
  content varchar(2000),
  writer varchar(255),
  reg_date timestamp default now(),
  mod_date timestamp,
  primary key(id),
  foreign key(writer) references tbl_members(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);


```

---
