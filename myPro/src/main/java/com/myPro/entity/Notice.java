package com.myPro.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
	private int notice_bno;
	private int bno;
	private String id;
	private String notice_content;
	private String notice_id;
	private Date notice_date;
}
