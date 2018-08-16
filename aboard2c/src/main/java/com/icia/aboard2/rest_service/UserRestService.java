package com.icia.aboard2.rest_service;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.icia.aboard2.dao.UserRepository;
import com.icia.aboard2.dto.UserDto.LoginUser;
import com.icia.aboard2.entity.User;


@Service
public class UserRestService {
	@Autowired
	private UserRepository dao;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder encoder;
	
	
	public boolean idCheck(String id) {
		return dao.idCheck(id)==null? true: false;
	}
	public boolean login(LoginUser logi) {
		User user = mapper.map(logi, User.class);
		if(dao.login(user)==null) {
			return false;
		}	
		if(dao.login(user)!=null) {
			String encodedPwd = dao.login(user).get("PWD").toString();
			if(encoder.matches(user.getPwd(), encodedPwd))
				return true;
		}
		return false;
		
	}
	public Map<String, Object> getEmail(String id) {
		return dao.getEmail(id);
	}
}
