package org.zerock.domain;

//Criteria == '검색 기준,분류 기준'
public class Criteria {	
	private int page;
	private int perPageNum;
	
	public Criteria() {
		//System.out.println("Criteria생성");
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page) {
		//System.out.println("Cri.setPage호출");
		if(page <= 0) {
			this.page = 1;
			return;
		}		
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {
		//System.out.println("Cri.setPerPageNum호출");
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;		
	}
	
	public int getPage() {
		return page;
	}
	
	//method for MyBatis SQL Mapper
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}
	
	//method for MyBatis SQL Mapper
	public int getPerPageNum() {
		return perPageNum;
	}
	
	@Override
	public String toString() {
		return "Criteria [page="+page+", perPageNum:"+perPageNum+"]";
	}
}
