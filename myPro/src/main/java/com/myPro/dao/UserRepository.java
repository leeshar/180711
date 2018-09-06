package com.myPro.dao;

import java.util.List;
import java.util.Map;

import com.myPro.dto.UserDto.FindId;
import com.myPro.entity.Notice;
import com.myPro.entity.User;

public interface UserRepository {
	// 회원정보
	Map<String, Object> uInformation(String id);
	// 회원가입
	void uJoin(User user);
	// 회원권한
	String uAuthorities(String id);
	// 비밀번호가져오기
	String uUserPwd(String id);
	// 아이디 찾기
	String uFindId(FindId findId);
	// 아이디 중복확인
	String uIdCheck(String id);
	// 로그인 인증
	Map<String, Object> uLoginCheck(User user);
	// 권한넣기
	void uInsertAuthority(String id);
	// 회원정보수정
	int uUserUpdate(User user);
	// 이메일 가져오기
	Map<String, Object> uUserEmail(String id);
	// 비밀번호 초기화
	int uUserPwdSet(User user);
	// 알림확인
	List<Notice> uNotice(String id);
}
