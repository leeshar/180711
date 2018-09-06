package com.myPro.service;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
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

import com.myPro.dao.UserRepository;
import com.myPro.dto.UserDto.CreateUser;
import com.myPro.dto.UserDto.FindId;
import com.myPro.dto.UserDto.LoginUser;
import com.myPro.dto.UserDto.UpdateUser;
import com.myPro.entity.Notice;
import com.myPro.entity.User;
import com.myPro.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userDao;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JavaMailSender mailSender;

	// uInformation
		public Map<String, Object> uInformation(String id) {
			return userDao.uInformation(id);
	}
	// uJoin
		@Transactional
		public void uJoin(CreateUser create) {
		User user = mapper.map(create, User.class);
		user.setPwd(encoder.encode(user.getPwd()));
		String id = user.getId();
		userDao.uInsertAuthority(id);
		userDao.uJoin(user);
	}
	// uFindId
		public String uFindId(FindId findId) throws FileNotFoundException, URISyntaxException {

			return userDao.uFindId(findId);

	}
	// uIdCheck
		public boolean uIdCheck(String id) {
			return userDao.uIdCheck(id) == null ? true : false;
	}
	// uLoginCheck
		@Transactional
		public String uLoginCheck(LoginUser logi) {
			User user = mapper.map(logi, User.class);
		if(userDao.uIdCheck(logi.getId())!=null) {	
			try {
			String encodedPwd = userDao.uLoginCheck(user).get("PWD").toString();
			if (encoder.matches(user.getPwd(), encodedPwd))
				return "성공";
			else
				return "비밀번호다름";
			}catch(NullPointerException np) {
				System.out.println("NULL");
			}
		}
			return "아이디다름";
	}
	// uUserUpdate
		public void uUserUpdate(UpdateUser u) {
		u.setPwd(encoder.encode(u.getPwd()));// 패스워드 암호화
		User user = mapper.map(u, User.class);
		if (userDao.uUserUpdate(user) == 0)
			throw new UserNotFoundException(user.getId());
	}
	// sendMail
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
	// uUserPwdSet
		public void uUserPwdSet(String id, String pwd, String email) throws FileNotFoundException, URISyntaxException {
		log.info("{}", pwd);
		User user = new User();
		user.setId(id);
		user.setPwd(encoder.encode(pwd));
		userDao.uUserPwdSet(user);

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
	// getRandomPassword
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
	// uUserEmail
		public Map<String, Object> uUserEmail(String id) {
		return userDao.uUserEmail(id);
	}
	// uNotice
		public List<Notice> uNotice(String id){
		return userDao.uNotice(id);
	}
}
