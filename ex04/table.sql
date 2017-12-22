create table tbl_board(
	bno INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(200) NOT NULL,
	content TEXT NULL,
	writer VARCHAR(50) NOT NULL,
	regdate TIMESTAMP NOT NULL DEFAULT now(),
	viewcnt INT default 0,
	primary key (bno)
	);
	
	//board 예제 삽입하는 방법
	insert into tbl_board (title,content,writer) (select title,content,writer from tbl_board);
	
create table tbl_reply(
	rno int not null AUTO_INCREMENT,
    bno int not null default 0,
    replytext varchar(1000) not null,
    replyer varchar(50) not null,
    regdate timestamp not null default now(),
    updatedate timestamp not null default now(),
    primary key(rno)
    );
    
alter table tbl_reply add constraint fk_board foreign key (bno) references tbl_board (bno);


create table tbl_attach (
	fullName varchar(150) not null,
    bno int not null,
    regdate timestamp default now(),
    primary key (fullName)
);

alter table tbl_attach add constraint fk_board_attach foreign key (bno) references tbl_board (bno);