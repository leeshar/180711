package com.icia.aboard2.service;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Random;

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
import com.icia.aboard2.dto.UserDto.LoginUser;
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

	// 회원가입
	@Transactional
	public void join(CreateUser create) {
		User user = mapper.map(create, User.class);
		user.setPwd(encoder.encode(user.getPwd()));
		String id = user.getId();
		dao.authorities(id);
		dao.insertUser(user);
	}

	// 회원정보수정
	public void updateUser(UpdateUser u) {
		u.setPwd(encoder.encode(u.getPwd()));// 패스워드 암호화
		User user = mapper.map(u, User.class);
		if (dao.updateUser(user) == 0)
			throw new UserNotFoundException(user.getId());
	}

	// 회원정보
	public Map<String, Object> userInfo(String id) {
		return dao.information(id);
	}

	// 인증번호
	public void sendMail(String email, String authToken) throws FileNotFoundException, URISyntaxException {

		try {

			MimeMessage message = mailSender.createMimeMessage();

			log.info("{}", authToken);
			String setfrom = "snrntpa2z@gmail.com";
			String tomail = email; // 받는 사람 이메일
			String title = "아이디 찾기 인증번호입니다"; // 제목
			String content = "인증번호를 입력해주세요 : " + authToken; // 내용

			message.setFrom(new InternetAddress(setfrom));
			message.addRecipient(RecipientType.TO, new InternetAddress(tomail));
			message.setSubject(title);
			message.setText(content, "utf-8", "html");
			mailSender.send(message);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	// 비밀번호 리셋
	public void pwdMail(String email, String pwd) throws FileNotFoundException, URISyntaxException {
		User user = new User();
		user.setEmail(email);
		user.setPwd(encoder.encode(pwd));
		dao.pwdReset(user);

		try {

			MimeMessage message = mailSender.createMimeMessage();

			String setfrom = "snrntpa2z@gmail.com";
			String tomail = email; // 받는 사람 이메일
			String title = "비밀번호 찾기 메일입니다 "; // 제목
			String content = "비밀번호가 초기화 되었습니다." + pwd + "비밀번호를 바꿔주세요"; // 내용

			message.setFrom(new InternetAddress(setfrom));
			message.addRecipient(RecipientType.TO, new InternetAddress(tomail));
			message.setSubject(title);
			message.setText(content, "utf-8", "html");
			mailSender.send(message);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	// 비밀번호 생성
	public static String getRandomPassword(int length) {
		char[] charaters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuilder sb = new StringBuilder("");
		Random rn = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(charaters[rn.nextInt(charaters.length)]);
		}
		return sb.toString();
	}

	// 아이디 찾기
	public String findId(FindId findId) throws FileNotFoundException, URISyntaxException {

		return dao.findId(findId);

	}

	// 아이디 중복확인
	public boolean idCheck(String id) {
		return dao.idCheck(id) == null ? true : false;
	}

	// 로그인인증
	@Transactional
	public String login(LoginUser logi) {
		User user = mapper.map(logi, User.class);
	if(dao.loginCheck(logi.getId())!=null) {	
		String encodedPwd = dao.login(user).get("PWD").toString();
		if (encoder.matches(user.getPwd(), encodedPwd))
			return "성공";
		else
			return "비밀번호다름";
	}
		return "아이디다름";
	}

	// 이메일 가져오기
	public Map<String, Object> getEmail(String id) {
		return dao.getEmail(id);
	}
}
