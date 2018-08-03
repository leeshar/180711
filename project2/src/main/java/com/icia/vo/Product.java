package com.icia.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private int product_id;
	private String product_name;
	private int price;
	private String photo_url;
	private int read_cnt;
	private String seller;
	private int like_cnt;
}
