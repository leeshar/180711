package com.icia.aboard2.service;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.icia.aboard2.dao.UserRepository;
import com.icia.aboard2.dto.UserDto.CreateUser;
import com.icia.aboard2.dto.UserDto.FindId;
import com.icia.aboard2.dto.UserDto.UpdateUser;
import com.icia.aboard2.entity.User;
import com.icia.aboard2.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository dao;
	@Autowired
	private Gson gson;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JavaMailSender mailSender;
	@Transactional
	public void join(CreateUser create) {
		User user = mapper.map(create, User.class);
		user.setPwd(encoder.encode(user.getPwd()));
		String id = user.getId();
		dao.authorities(id);
		dao.insertUser(user);
	}

	public void update(UpdateUser u) {
		User user = mapper.map(u, User.class);
		if(dao.update(user)==0)
			throw new UserNotFoundException(user.getId());
	}
// 인증번호
public void sendMail(String email,String authToken) throws FileNotFoundException, URISyntaxException {

 try{

  MimeMessage message = mailSender.createMimeMessage();

  log.info("{}", authToken);
  String setfrom = "snrntpa2z@gmail.com";        
  String tomail  = email;    // 받는 사람 이메일
  String title   = "아이디 찾기 인증번호입니다";      // 제목
  String content = "인증번호를 입력해주세요 : " + authToken;    // 내용

  message.setFrom(new InternetAddress(setfrom));  
  message.addRecipient(RecipientType.TO, new InternetAddress(tomail));
  message.setSubject(title);
  message.setText(content, "utf-8", "html");
  mailSender.send(message);

 }catch(Exception e){

  e.printStackTrace();

 }

}

public String findId(FindId findId) throws FileNotFoundException, URISyntaxException {
	
	return dao.findId(findId);
	
	
}

}

