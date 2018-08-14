package com.icia.aboard2.controller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.UserDto.FindId;
import com.icia.aboard2.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	private ObjectMapper mapper;
	//인증번호발송
	@RequestMapping("/users/findId/emailAuth")
	@ResponseBody
	public void emailAuth(String mail) throws FileNotFoundException, URISyntaxException, ParseException {
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(mail);
		String authToken = obj.get("authToken").toString();
		String email = obj.get("email").toString();
		service.sendMail(email,authToken);
		
		
	}
	@RequestMapping("/users/findId")
	@ResponseBody
	public ResponseEntity<String> findId(String find) throws FileNotFoundException, URISyntaxException, ParseException, JsonProcessingException {
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(find);
		FindId findId = new FindId();
		findId.setEmail(obj.get("email").toString());
		findId.setIrum(obj.get("irum").toString());
		String result = service.findId(findId);
		if(service.findId(findId)!=null)
		return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
		
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
	
}




