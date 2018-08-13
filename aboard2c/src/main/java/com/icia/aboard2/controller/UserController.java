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

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	
	
}




