package com.myPro.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
	private int ano;
	private String originalFileName;
	private String savedFileName;
	private int bno;
}
