package org.zerock.domain;

import java.util.Date;

public class BoardVO {
	
	public BoardVO() {
		System.out.println("BoardVO() 생성");
	}
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewcnt;
		
	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		System.out.println("setBno : "+bno);
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		System.out.println("setTitle : "+title);
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		System.out.println("setContent : "+content);
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		System.out.println("setWriter : "+writer);
		this.writer = writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		System.out.println("setRegdate : "+regdate);
		this.regdate = regdate;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int viewcnt) {
		System.out.println("setViewcnt : "+viewcnt);
		this.viewcnt = viewcnt;
	}
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + ", viewcnt=" + viewcnt + "]";
	}
}
