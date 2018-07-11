package com.icia.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private int cno;
	private int product_id;
	private String replytext;
	private String id;
	private Date write_date;
	private Date update_date;
	
}
