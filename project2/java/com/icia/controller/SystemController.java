package com.icia.controller;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class SystemController {
	
	@ExceptionHandler(BindException.class)
	public String bindExceptionHandler(BindingResult result, Model model) {
	List<ObjectError> list =  result.getAllErrors();
	List<String> msgs = new ArrayList<String>(); 
	for(ObjectError oe : list) { 
		FieldError fe = (FieldError)oe;
		msgs.add(fe.getField() + ":" +  oe.getDefaultMessage());
	}
	
	model.addAttribute("msgs", msgs);

	return "system/exceptions";
}
}