package com.icia.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {
	private int page = 1;
	private int perPageNum = 9;
	public Criteria() {
		
		this.page = 1;
		this.perPageNum = 9;
	}
	public void setPage(int page) {
		if(page<=0) {
			this.page =1;
			return;
		}
		this.page = page;
	}
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	public int getPage() {
		return page;
	}
	public int getPerPageNum() {
		return this.perPageNum;
	}
	@Override
	public String toString() {
		return "Criteria [page=" + page + ","
				+ "perPageNum=" + perPageNum + "]";
	}
}
