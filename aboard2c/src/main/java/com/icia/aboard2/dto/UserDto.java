package com.icia.aboard2.dto;

import java.util.*;

import org.springframework.format.annotation.*;

import lombok.*;

// 사용자와 입출력할 DTO 클래스들을 담을 컨테이너
public class UserDto {
	// join할 때 값 받아올 DTO
	@Data
	public static class CreateUser {  // 사용자 -> DB join
		private String id;
		private String pwd;
		private String irum;
		private String email;
	}
	@Data
	public static class LoginUser{
		private String id;
		private String pwd;
		
	}
	@Data
	public static class FindId{
		private String irum;
		private String email;
	}
	@Data
	public static class PwdReset{
		private String email;
		private String pwd;
	}
	@Data
	public static class ChangeUserPwd {
		private String id;
		private String oldPwd;
		private String newPwd;
	}
	@Data
	public static class UpdateUser {
		private String id;
		private String irum;
		private String email;
	}
}





