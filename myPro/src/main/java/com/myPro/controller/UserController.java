package com.myPro.controller;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myPro.dto.UserDto.CreateUser;
import com.myPro.dto.UserDto.FindId;
import com.myPro.dto.UserDto.LoginUser;
import com.myPro.dto.UserDto.UpdateUser;
import com.myPro.service.UserService;



@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper mapper;
	//uInformation
		@RequestMapping(value="/users/userInfo/{id}",produces = "application/json; charset=UTF-8")
		public String uInformation(@PathVariable String id) throws JsonProcessingException {
				return mapper.writeValueAsString(userService.uInformation(id));
	}
	//uJoin
		@RequestMapping("/users/join")
		public ResponseEntity<Void> uJoin(String user) throws ParseException {
			JSONObject obj = JsonPaser(user);
			CreateUser create = new CreateUser();
			create.setId(obj.get("id").toString());
			create.setPwd(obj.get("pwd").toString());
			create.setEmail(obj.get("email").toString());
			create.setIrum(obj.get("irum").toString());
			create.setBirthDay(obj.get("realBirth").toString());
			create.setAddress(obj.get("realAddress").toString());
			userService.uJoin(create);
			return new ResponseEntity<>(HttpStatus.OK);
	}
	//uIdCheck
		@RequestMapping("/users/idCheck/{id}")
		public ResponseEntity<Void> uIdCheck(@PathVariable String id) {
			boolean result = userService.uIdCheck(id);
			//아이디가 없으면 OK, 있으면 BAD_REQUEST
			if(result)
				return new ResponseEntity<>(HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	//uLoginCheck
		@RequestMapping(value="/users/login",produces = "application/json; charset=UTF-8")
		public ResponseEntity<String> uLoginCheck(String login) throws ParseException, JsonProcessingException, InterruptedException{
			JSONObject obj = JsonPaser(login);
			LoginUser logi = new LoginUser();
			logi.setId(obj.get("id").toString());
			logi.setPwd(obj.get("pwd").toString());
			String loginMsg = userService.uLoginCheck(logi);
			System.out.println(loginMsg);
			if(loginMsg=="아이디다름") {
				//쓰레드로 로딩 지연
				Thread.sleep(1000);
				return new ResponseEntity<String>(mapper.writeValueAsString(loginMsg),HttpStatus.OK);
		}
			if(loginMsg=="비밀번호다름") {
				//쓰레드로 로딩 지연
				Thread.sleep(1000);
				return new ResponseEntity<String>(mapper.writeValueAsString(loginMsg),HttpStatus.OK);
		}
			// SUCCESS
		Thread.sleep(1000);
		return new ResponseEntity<String>(mapper.writeValueAsString(loginMsg),HttpStatus.OK);
		
		
	}
	//emailAuth
		@RequestMapping("/users/findId/emailAuth")
		public void emailAuth(String mail) throws FileNotFoundException, URISyntaxException, ParseException, InterruptedException {
			JSONObject obj = JsonPaser(mail);
			String authToken = obj.get("authToken").toString();
			String email = obj.get("email").toString();
			Thread.sleep(1000);
			userService.sendMail(email,authToken);
	}
	//uUserPwdSet
		@RequestMapping("/users/findPwd/emailAuth")
		public void uUserPwdSet(String id) throws FileNotFoundException, URISyntaxException {
			String pwd = userService.getRandomPassword(10);
			String email = userService.uUserEmail(id).get("REALEMAIL").toString();
			userService.uUserPwdSet(id,pwd,email);
	}
	//uFindId
		@RequestMapping("/users/findId")
		public ResponseEntity<String> uFindId(String find) throws FileNotFoundException, URISyntaxException, ParseException, JsonProcessingException {
			JSONObject obj = JsonPaser(find);
			FindId findId = new FindId();
			findId.setEmail(obj.get("email").toString());
			findId.setIrum(obj.get("irum").toString());
			
			if(userService.uFindId(findId)!=null) {
				String result = userService.uFindId(findId);
				return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
		}	
			String result = "NO";
			// 아이디가 없을 경우
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	//uFindPwd
		@RequestMapping(value="/users/findPwd",produces = "application/json; charset=UTF-8")
		public ResponseEntity<String> uFindPwd(String id) throws JsonProcessingException{
			// service.getEmail <= NULL 일 경우는 ID가 존재하지 않는다
			if(userService.uUserEmail(id)==null) {
				Object result = "해당이메일없음";
				return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
			}
			// NULL이 아닐경우 담아서 보냄
			Object result = userService.uUserEmail(id).get("EMAIL");
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);	
	}
	//uUserUpdate
		@RequestMapping(value="/users/updateUser")
		public ResponseEntity<Void> uUserUpdate(String update) throws ParseException{
			JSONObject obj = JsonPaser(update);
			UpdateUser u = new UpdateUser();
			u.setEmail(obj.get("email").toString());
			u.setId(obj.get("id").toString());
			u.setPwd(obj.get("pwd").toString());
			userService.uUserUpdate(u);
			return new ResponseEntity<Void>(HttpStatus.OK);		
	}
	//uNotice
		@RequestMapping(value="/users/notice",produces = "application/json; charset=UTF-8")
		public ResponseEntity<String> uNotice(String id) throws Exception{
			System.out.println("내 아이디"+id);
			userService.uNotice(id);
			System.out.println(userService.uNotice(id));
			return new ResponseEntity<String>(mapper.writeValueAsString(userService.uNotice(id)),HttpStatus.OK);
			
	}
	// 중복 코드 제거
		private JSONObject JsonPaser(String user) throws ParseException {
			JSONParser jsonParser = new JSONParser();
			JSONObject obj = (JSONObject) jsonParser.parse(user);
			return obj;
	}
}