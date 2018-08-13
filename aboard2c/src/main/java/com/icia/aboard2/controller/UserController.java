package com.icia.aboard2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.UserDto;
import com.icia.aboard2.service.UserService;
import com.icia.aboard2.util.ABoard2Util;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:6305/aboard2" , maxAge=3600)
@Controller
@Slf4j
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	private ObjectMapper mapper;
	// 유저 정보 변경 : update
	@PostMapping("/users/update")
	public String update(@Valid UserDto.UpdateUser user, BindingResult results, RedirectAttributes ra) throws BindException {
		ABoard2Util.throwBindException(results);
		service.update(user);
		ra.addFlashAttribute("msg", "정보를 변경했습니다");
		return "redirect:/";
	}
	
}




