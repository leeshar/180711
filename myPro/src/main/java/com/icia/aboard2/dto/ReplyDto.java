package com.icia.aboard2.dto;

import java.util.Date;


import lombok.Data;

public class ReplyDto {
	// 댓글 작성
	@Data
	public static class InsertReply {
		private int cno;
		private int bno;
		private String id;
		private String replytext;
		private Date write_date;
	}
	// 댓글 삭제
	@Data
	public static class DeleteReply{
		private String cno;
		private String id;
	}
	// 댓글 리스트
	@Data
	public static class Response {
		private Integer cno;
		private String id;
		private String replytext;
		private Date write_date;
	}
	@Data
	public static class NoticeReply{
		private int bno;
		private String id;
		private String notice_content;
		private String notice_id;
		
	}
	
}
