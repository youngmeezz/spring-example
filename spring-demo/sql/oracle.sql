create table person (
  person_id number PRIMARY  key,
  name varchar2(20) not null,
  age number not null
);

create sequence person_seq;
