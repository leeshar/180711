package com.icia.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.icia.service.BoardService;
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
	
	// 게시글 목록


	@GetMapping("/board/list")
	@Secured("ROLE_USER")
	public void listPage(Criteria cri, Model model) throws Exception{
		Paging paging = new Paging();
		paging.setCri(cri);
		paging.setTotalCount(131);
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
	public String read(int product_id, Model model) throws Exception {
		model.addAttribute("product", service.read(product_id));
		model.addAttribute("state", "update");
		log.info("{}",service.read(product_id));
		return "board/read";
	}
	
	// 글 쓰기 (첨부파일)
	@GetMapping("/board/write")
	@Secured("ROLE_USER")
	public String write() {
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
	
}
