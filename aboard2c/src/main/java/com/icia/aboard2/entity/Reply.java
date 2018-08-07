package com.icia.aboard2.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private int cno;
	private int bno;
	private String replytext;
	private String id;
	private Date write_date;
	
	
	
}
