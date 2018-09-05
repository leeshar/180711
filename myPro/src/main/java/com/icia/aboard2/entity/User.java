package com.icia.aboard2.entity;



import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private String id;
	private String pwd;
	private String irum;
	private String email;
	private String birthDay;
	private String address;
}
