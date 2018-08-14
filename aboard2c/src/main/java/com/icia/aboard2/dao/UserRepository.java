package com.icia.aboard2.dao;

import java.util.Map;

import com.icia.aboard2.dto.UserDto.FindId;
import com.icia.aboard2.entity.User;

public interface UserRepository {

	void insertUser(User user);
	
	void authorities(String id);

	String getPwd(String id);

	String findId(FindId findId);
	
	void pwdChange(User user);
	
	String idCheck(String id);

	Map<String, Object> login(User user);
	
	int update(User user);
	
	Map<String, Object> getEmail(String id);
	
	int pwdReset(User user);
}
