create table tbl_board(
	bno INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(200) NOT NULL,
	content TEXT NULL,
	writer VARCHAR(50) NOT NULL,
	regdate TIMESTAMP NOT NULL DEFAULT now(),
	viewcnt INT default 0,
	primary key (bno));
	
	//board 예제 삽입하는 방법
	insert into tbl_board (title,content,writer) (select title,content,writer from tbl_board);