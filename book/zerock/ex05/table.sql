create table tbl_user(
	uid varchar(50) not null,
    upw varchar(100) not null,
    uname varchar(100) not null,
    upoint int not null default 0,
	sessionkey varchar(50) not null default 'none',
	sessionlimit timestamp,
    primary key(uid)	
);

create table tbl_board(
	bno int not null auto_increment,
	title varchar(200) not null,
	content TEXT,
	writer varchar(50),
	regdate timestamp default now(),
	viewcnt int default 0,
	replycnt int default 0,    
	primary key(bno),    
    foreign key (writer) references tbl_user(uid)
);


create table tbl_reply(
	rno int not null auto_increment,
	bno int not null,
	replytext varchar(1000) not null,
	replyer varchar(50),
	regdate timestamp default now(),
	updatedate timestamp default now(),
	primary key(rno),
	foreign key (bno) references tbl_board(bno)
);

create table tbl_attach(
	fullName varchar(150) not null,
	bno int not null,
	regdate timestamp default now(),
	primary key(fullName),	
	foreign key (bno) references tbl_board(bno)
);


	
insert into tbl_user (uid,upw,uname) values('user00','user00','IRON MAN');
insert into tbl_user (uid,upw,uname) values('user01','user01','CAPTAIN');
insert into tbl_user (uid,upw,uname) values('user02','user02','HULK');
insert into tbl_user (uid,upw,uname) values('user03','user03','Thor');
insert into tbl_user (uid,upw,uname) values('user10','user10','Quick Silver');