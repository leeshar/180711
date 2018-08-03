package com.icia.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventBoard {
	private int ebno;
	private String title;
	private String content;
	private String event_photo;
	private String writer;
	private Date write_date;
	private int read_cnt;
}
