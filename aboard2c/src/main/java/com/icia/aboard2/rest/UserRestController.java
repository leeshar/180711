package com.icia.aboard2.rest;


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
import org.springframework.web.bind.annotation.RestController;

import com.icia.aboard2.dto.UserDto.ChangeIrum;
import com.icia.aboard2.dto.UserDto.ChangeUserPwd;
import com.icia.aboard2.dto.UserDto.CreateUser;
import com.icia.aboard2.rest_service.UserRestService;
import com.icia.aboard2.service.UserService;
import com.icia.aboard2.util.ABoard2Util;

@RestController
public class UserRestController {
	@Autowired
	private UserRestService service;
	@Autowired
	private UserService uService;
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
	
	// 이름 변경 : irumChange
	@PostMapping("/users/changeIrum")
	public ResponseEntity<Void> changeIrum(@Valid ChangeIrum user, BindingResult results) throws BindException {
		ABoard2Util.throwBindException(results);
		service.irumChange(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}






