package com.icia.aboard2.dto;

import java.util.Date;


import lombok.Data;

public class ReplyDto {
	@Data
	public static class InsertReply {
		private int cno;
		private int bno;
		private String id;
		private String replytext;
		private Date write_date;
	}
	
	@Data
	public static class Response {
		private Integer cno;
		private String id;
		private String replytext;
		private Date write_date;
	}
	
}
