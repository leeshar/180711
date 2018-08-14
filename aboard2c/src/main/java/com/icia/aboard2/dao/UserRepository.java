package com.icia.aboard2.dao;

import org.apache.ibatis.annotations.*;

import com.icia.aboard2.entity.*;

public interface UserRepository {

	void insertUser(User user);
	
	void authorities(String id);
	
	User read(String id);

	String getPwd(String id);

	void pwdChange(User user);
	
	int irumChange(User user);

	String idCheck(String id);

	int update(User user);
}
