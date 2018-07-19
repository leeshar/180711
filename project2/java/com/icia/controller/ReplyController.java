package com.icia.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icia.service.ReplyService;
import com.icia.vo.Reply;

@RestController
public class ReplyController {
	@Autowired
	private ReplyService service;

	
	@RequestMapping("/board/reply/insert")
	public void insert(@ModelAttribute Reply reply,Principal principal)throws Exception{
		String id = principal.getName();
		reply.setId(id);
		service.insert(reply);
		
		
	}
	@PostMapping("/board/reply/update")
	public void update(Reply reply)throws Exception{
		service.update(reply);
	}
	@RequestMapping("/board/reply/delete/{cno}")
	public void delete(@PathVariable int cno)throws Exception{
		service.delete(cno);
	}
	
	@RequestMapping("/board/reply/list")
	public List<Reply> list(@RequestParam int product_id, Model model)throws Exception{
		model.addAttribute("list",service.list(product_id));
	return service.list(product_id);
		
		
		
		
	}
	@RequestMapping("/eventBoard/reply/insert")
	public void eBoardinsert(@ModelAttribute Reply reply,Principal principal)throws Exception{
		String id = principal.getName();
		reply.setId(id);
		service.eBoardinsert(reply);
		
		
	}
	@PostMapping("/eventBoard/reply/update")
	public void eBoardupdate(Reply reply)throws Exception{
		service.eBoardupdate(reply);
	}
	@RequestMapping("/eventBoard/reply/delete/{cno}")
	public void eBoarddelete(@PathVariable int cno)throws Exception{
		service.eBoarddelete(cno);
	}
	
	@RequestMapping("/eventBoard/reply/list")
	public List<Reply> eBoardlist(@RequestParam int ebno, Model model)throws Exception{
		model.addAttribute("list",service.list(ebno));
	return service.eBoardlist(ebno);
		
		
		
		
	}
	
}
