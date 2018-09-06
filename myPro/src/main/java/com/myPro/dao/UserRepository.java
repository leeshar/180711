package com.myPro.dao;

import java.util.List;
import java.util.Map;

import com.myPro.dto.UserDto.FindId;
import com.myPro.entity.Notice;
import com.myPro.entity.User;

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
	// 회원권한확인
	String authoritySearch(String id);
	// 알림확인
	List<Notice> notice(String id);
}
