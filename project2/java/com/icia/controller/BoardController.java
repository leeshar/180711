package com.icia.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.service.BoardService;
import com.icia.service.MemberService;
import com.icia.vo.Criteria;
import com.icia.vo.Paging;
import com.icia.vo.Product;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
		// Parameter ? Value ? @Valid Bean ?
		// Model ?
	@Autowired
	private BoardService service;
	@Autowired
	private MemberService mService;
	// 게시글 목록
	@Autowired
	private ObjectMapper mapper;

	@GetMapping("/board/list")
	@Secured("ROLE_USER")
	public void listPage(Criteria cri, Model model,Principal principal) throws Exception{
		Paging paging = new Paging();
		paging.setCri(cri);
		paging.setTotalCount(131);
		String id = principal.getName();
		model.addAttribute("list", mService.read(id));
		model.addAttribute("product", service.listCriteria(cri));
		model.addAttribute("paging", paging);
	
	}
	@GetMapping("/board/reply")
	public String replyCount(int product_id, RedirectAttributes ra) throws Exception {
		ra.addFlashAttribute("replyCnt", service.replyCount(product_id));
		return "redirect:/board/list";
	}
	// 글 읽기
	@GetMapping("/board/read")
	@Secured("ROLE_USER")
	public String read(int product_id, Model model,Principal principal) throws Exception {
		model.addAttribute("product", service.read(product_id));
		String id = principal.getName();
		model.addAttribute("list", mService.read(id));
		model.addAttribute("state", "update");
		log.info("{}",service.read(product_id));
		return "board/read";
	}
	
	// 글 쓰기 (첨부파일)
	@GetMapping("/board/write")
	@Secured("ROLE_USER")
	public String write(Model model, Principal principal) throws Exception {
		String id = principal.getName();
		model.addAttribute("list", mService.read(id));
		return "board/write";
	}
	@PostMapping("/board/write")
	@Secured("ROLE_USER")
	public String write(Product product,@RequestParam(required=false) MultipartFile image, RedirectAttributes ra, Principal principal) throws Exception {
		String seller =principal.getName();
		product.setSeller(seller);
		service.write(product, image);
		ra.addFlashAttribute("msg", "WRITE");
		return "redirect:/board/list";
	}
	// 글 수정
	@PostMapping("/board/update")
	@Secured("ROLE_USER")
	public String update(Product product, MultipartFile image, RedirectAttributes ra) throws Exception {
		service.updata(product, image);
		ra.addFlashAttribute("msg", "UPDATE");
		return "redirect: /board/list";
	}
	// 글 삭제
	@PostMapping("/board/delete")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public String delete(Product product) throws Exception {
		log.info("{}", product);
		service.delete(product);
		return "redirect: /board/list";
	}

	@PostMapping("/like/count")
	public ResponseEntity<String> count(@RequestParam String data,Principal principal) throws Exception {
	
		int product_id = Integer.parseInt(data);
		String result = service.like_increase(product_id,principal);
		String id = principal.getName();
		log.info("{}",result);
		log.info("{}", id);
		if(result.equals(id)) {
			return new ResponseEntity<>("not accpeted", HttpStatus.BAD_REQUEST);
			
		}
		else
			return new ResponseEntity<>(mapper.writeValueAsString(result),HttpStatus.OK);	
	}
	@RequestMapping(value="/board/search", produces = "application/text; charset=utf8")
	@ResponseBody
	public ResponseEntity<String> serachText(@RequestParam String data) throws Exception{
		
		Map<String, Object> map = new HashMap<>();
		List<?> product = service.allRead();
		map.put("product",product);
		if(data=="") {
			return new ResponseEntity<>("not accepted",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(mapper.writeValueAsString(product),HttpStatus.OK);
	}
	
	
}
