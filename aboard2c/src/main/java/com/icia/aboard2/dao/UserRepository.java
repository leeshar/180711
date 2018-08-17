package com.icia.aboard2.dao;

import java.util.Map;

import com.icia.aboard2.dto.UserDto.FindId;
import com.icia.aboard2.entity.User;

public interface UserRepository {
	// 회원정보
	Map<String, Object> information(String id);
	// 회원가입
	void insertUser(User user);
	// 회원권한
	void authorities(String id);
	// 비밀번호가져오기
	String getPwd(String id);
	// 아이디 찾기
	String findId(FindId findId);
	// 아이디 중복확인
	String idCheck(String id);
	// 로그인 인증
	Map<String, Object> login(User user);
	// 로그인 아이디확인
	String loginCheck(String id);
	// 회원정보수정
	int updateUser(User user);
	// 이메일 가져오기
	Map<String, Object> getEmail(String id);
	// 비밀번호 초기화
	int pwdReset(User user);
}
