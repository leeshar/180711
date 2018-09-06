package com.myPro.dto;

import java.util.*;


import lombok.*;

public class BoardDto {
	// 글 쓰기
	@Data
	public static class CreateBoard {
		private Integer bno;
		private String title;
		private String content;
		private String writer;
		private String categoriName;
	}
	// 게시판에 글 리스트
	@Data
	public static class ListBoard {
		private Integer bno;
		private String title;
		private String writer;
		private Date writeDate;
		private Integer readCnt;
		private Integer replyCnt;
		private Boolean isAttachExist;
	}
	// 글 수정
	@Data
	public static class UpdateBoard{
		private String bno;
		private String title;
		private String content;
		private String categoriName;
	}
}

