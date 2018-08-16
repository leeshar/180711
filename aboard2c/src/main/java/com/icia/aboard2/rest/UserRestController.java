package com.icia.aboard2.rest;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.UserDto.ChangeUserPwd;
import com.icia.aboard2.dto.UserDto.CreateUser;
import com.icia.aboard2.dto.UserDto.FindId;
import com.icia.aboard2.dto.UserDto.LoginUser;
import com.icia.aboard2.rest_service.UserRestService;
import com.icia.aboard2.service.UserService;
import com.icia.aboard2.util.ABoard2Util;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class UserRestController {
	@Autowired
	private UserRestService service;
	@Autowired
	private UserService uService;
	@Autowired
	private ObjectMapper mapper;
	@PostMapping("/users/join")
	public ResponseEntity<Void> userJoin(String user) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject obj = (JSONObject) jsonParser.parse(user);
		CreateUser create = new CreateUser();
		create.setId(obj.get("id").toString());
		create.setPwd(obj.get("pwd").toString());
		create.setEmail(obj.get("email").toString());
		create.setIrum(obj.get("irum").toString());
		uService.join(create);
		return new ResponseEntity<>(HttpStatus.OK);
		
		
	}
	@RequestMapping("/users/idCheck/{id}")
	public ResponseEntity<Void> idCheck(@PathVariable String id) {
		boolean result = service.idCheck(id);
		if(result)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/users/changePwd")
	public ResponseEntity<Void> changePwd(@Valid ChangeUserPwd user, BindingResult results) throws BindException {
		ABoard2Util.throwBindException(results);
		service.pwdChange(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@PostMapping("/users/login")
	public ResponseEntity<String> login(String login) throws ParseException, JsonProcessingException, InterruptedException{
		JSONParser jsonParser = new JSONParser();
		JSONObject obj = (JSONObject) jsonParser.parse(login);
		LoginUser logi = new LoginUser();
		logi.setId(obj.get("id").toString());
		logi.setPwd(obj.get("pwd").toString());
		
		if(service.login(logi)) {
			Object success = true;
			log.info("{}", success);
			Thread.sleep(1000);
			return new ResponseEntity<String>(mapper.writeValueAsString(success),HttpStatus.OK);
		}
		Object failed = false;
		Thread.sleep(1000);
		return new ResponseEntity<String>(mapper.writeValueAsString(failed),HttpStatus.OK);
		
		
}
		//인증번호발송
		@RequestMapping("/users/findId/emailAuth")
		public void emailAuth(String mail) throws FileNotFoundException, URISyntaxException, ParseException {
			JSONParser jsonparser = new JSONParser();
			JSONObject obj = (JSONObject) jsonparser.parse(mail);
			String authToken = obj.get("authToken").toString();
			String email = obj.get("email").toString();
			uService.sendMail(email,authToken);
			
			
		}
		//비밀번호리셋
		@RequestMapping("/users/findPwd/emailAuth")
		public void pwdEmailAuth(String id) throws FileNotFoundException, URISyntaxException {
			String pwd = uService.getRandomPassword(10);
			String email = service.getEmail(id).get("REALEMAIL").toString();
			uService.pwdMail(email,pwd);
		}
		//아이디 찾기
		@RequestMapping("/users/findId")
		public ResponseEntity<String> findId(String find) throws FileNotFoundException, URISyntaxException, ParseException, JsonProcessingException {
			JSONParser jsonparser = new JSONParser();
			JSONObject obj = (JSONObject) jsonparser.parse(find);
			FindId findId = new FindId();
			findId.setEmail(obj.get("email").toString());
			findId.setIrum(obj.get("irum").toString());
			String result = uService.findId(findId);
			if(uService.findId(findId)!=null)
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
			
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		//비밀번호 찾기
		@RequestMapping("/users/findPwd")
		public ResponseEntity<String> findPwd(String id) throws JsonProcessingException{
			Object result = service.getEmail(id).get("EMAIL");
			log.info("{}", id);
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
			
			
		}
}






