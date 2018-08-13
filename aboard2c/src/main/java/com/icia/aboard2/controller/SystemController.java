package com.icia.aboard2.controller;


import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;


@Controller
public class SystemController {
	@GetMapping("/")
	public String index() {
		return "layOut";
	}
	
}
