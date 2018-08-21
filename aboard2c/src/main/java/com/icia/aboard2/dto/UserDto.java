package com.icia.aboard2.dto;


import lombok.*;

// 사용자와 입출력할 DTO 클래스들을 담을 컨테이너
public class UserDto {
	// 회원가입
	@Data
	public static class CreateUser {  // 사용자 -> DB join
		private String id;
		private String pwd;
		private String irum;
		private String email;
		private String birthDay;
		private String address;
	}
	// 로그인 인증
	@Data
	public static class LoginUser{
		private String id;
		private String pwd;
		
	}
	// 아이디 찾기
	@Data
	public static class FindId{
		private String irum;
		private String email;
	}
	// 비밀번호 리셋
	@Data
	public static class PwdReset{
		private String id;
		private String pwd;
	}
	// 회원 정보 수정
	@Data
	public static class UpdateUser {
		private String id;
		private String pwd;
		private String email;
	}
	
}





