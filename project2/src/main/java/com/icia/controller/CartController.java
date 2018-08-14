package com.icia.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.icia.service.CartService;
import com.icia.service.MemberService;
import com.icia.vo.Cart;


@Controller
public class CartController {
	@Autowired
	private CartService service;
	@Autowired
	private MemberService mservice;
	
	@PostMapping("/cart/buy")
	public String buy(Cart cart, Principal principal, int product_id) throws Exception {
		String id = principal.getName();
		cart.setId(id);
		service.insert(cart);
		return "redirect:/board/read?product_id="+product_id;
		
	}
	@GetMapping("/cart/list")
	public String cartList(Principal principal, Model model) throws Exception{
		String id = principal.getName();
		model.addAttribute("list", mservice.read(id));
		model.addAttribute("map", service.read(id));
		return "cart/list";
		
	}
	@GetMapping("/cart/delete")
	public String cartDelete(int cart_id)throws Exception {
		service.delete(cart_id);
		return "redirect:/cart/list";
		
	}
}
