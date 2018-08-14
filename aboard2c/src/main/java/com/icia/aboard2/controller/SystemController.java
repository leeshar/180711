package com.icia.aboard2.controller;


import javax.servlet.http.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;


@Controller
public class SystemController {
	@GetMapping("/")
	public String index(HttpServletRequest req) {
		return "layOut";
	}
	
}
