package com.icia.aboard2.dao;

import java.util.Map;

import com.icia.aboard2.dto.UserDto.FindId;
import com.icia.aboard2.entity.User;

public interface UserRepository {
	Map<String, Object> information(String id);
	
	void insertUser(User user);
	
	void authorities(String id);

	String getPwd(String id);

	String findId(FindId findId);

	String idCheck(String id);

	Map<String, Object> login(User user);
	
	int updateUser(User user);
	
	Map<String, Object> getEmail(String id);
	
	int pwdReset(User user);
}
