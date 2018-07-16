package com.icia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.service.MemberService;
import com.icia.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/*")
@Slf4j
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private ObjectMapper mapper;
	
	@GetMapping("/register")
	public String join() {
		return "member/register";
	}
	@PostMapping("/register")
	public String join(@RequestParam(required=false) MultipartFile image,Member member, RedirectAttributes ra) throws Exception {
		log.info("{}", member);
		service.join(member, image);
		ra.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("/idFind")
	public String idFind() {
		return "/member/idFind";
	}
	@PostMapping("/idFind")
	public String idFind(String irum, String email,Model model) throws Exception {
		model.addAttribute("id", service.idFind(irum, email));
		return "/member/idFind";
	}
	@GetMapping("/pwdFind")
	public String pwdFind() {
		return "/member/pwdFind";
	}
	@PostMapping("/pwdFind")
	public String pwdFind(String id, String email, Model model) throws Exception {
		model.addAttribute("pwd", service.pwdFind(id, email));
		return "/member/pwdFind";
	}
	@PostMapping("/update")
	public String update(Member member, RedirectAttributes ra) throws Exception {
		service.update(member);
		ra.addFlashAttribute("msg","SUCCESS");
		return "redirect:/member/read";
	}
	@PostMapping("/resign")
	public void resign(String id) throws Exception {
		service.resign(id);
	}
	@PostMapping("/idCheck")
	public ResponseEntity<String> idCheck(String id) throws JsonProcessingException{
		System.out.println("id:"+ id);
		if(id=="")
			return new ResponseEntity<>("not accpeted", HttpStatus.BAD_REQUEST);
		boolean result = service.idCheck(id);
		log.info("{}",result);
			return new ResponseEntity<>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	
	
}
