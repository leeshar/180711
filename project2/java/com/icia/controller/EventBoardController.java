package com.icia.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.icia.service.EventBoardService;
import com.icia.service.MemberService;
import com.icia.vo.Criteria;
import com.icia.vo.EventBoard;
import com.icia.vo.Paging;


import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EventBoardController {
	
	@Autowired
	private EventBoardService service;
	
	@Autowired
	private MemberService mService;
	
	@GetMapping("/eventBoard/list")
	@Secured("ROLE_USER")
	public String listPage(Criteria cri, Model model,Principal principal) throws Exception{
		Paging paging = new Paging();
		paging.setCri(cri);
		paging.setTotalCount(131);
		String id = principal.getName();
		if(service.listCriteria(cri).isEmpty()) {
			return "redirect:/eventBoard/noPage";
		}
		else {
		model.addAttribute("list", mService.read(id));
		model.addAttribute("eventBoard", service.listCriteria(cri));
		model.addAttribute("paging", paging);
			return "eventBoard/list";
		}
	}
	
	// 글 읽기
	@GetMapping("/eventBoard/read")
	@Secured("ROLE_USER")
	public String read(int ebno, Model model,Principal principal) throws Exception {
		model.addAttribute("eventBoard", service.read(ebno));
		String id = principal.getName();
		model.addAttribute("list", mService.read(id));
		model.addAttribute("state", "update");
		log.info("{}",service.read(ebno));
		return "eventBoard/read";
	}
	
	// 글 쓰기 (첨부파일)
	@GetMapping("/eventBoard/write")
	@Secured("ROLE_USER")
	public String write(Model model, Principal principal) throws Exception {
		String id = principal.getName();
		model.addAttribute("list", mService.read(id));
		return "eventBoard/write";
	}
	@PostMapping("/eventBoard/write")
	@Secured("ROLE_USER")
	public String write(EventBoard eventBoard,@RequestParam(required=false) MultipartFile image, RedirectAttributes ra, Principal principal) throws Exception {
		String writer =principal.getName();
		eventBoard.setWriter(writer);
		service.write(eventBoard, image);
		ra.addFlashAttribute("msg", "WRITE");
		return "redirect:/eventBoard/list";
	}
	// 글 수정
	@PostMapping("/eventBoard/update")
	@Secured("ROLE_USER")
	public String update(EventBoard eventBoard, MultipartFile image, RedirectAttributes ra) throws Exception {
		service.updata(eventBoard, image);
		ra.addFlashAttribute("msg", "UPDATE");
		return "redirect: /eventBoard/list";
	}
	// 글 삭제
	@PostMapping("/eventBoard/delete")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public String delete(EventBoard eventBoard) throws Exception {
		log.info("{}", eventBoard);
		service.delete(eventBoard);
		return "redirect: /eventBoard/list";
	}

	
	@RequestMapping(value="/eventBoard/search", method=RequestMethod.GET, produces = "application/text; charset=utf8")
	public void serachText(String term, HttpServletResponse response) throws Exception{
		List<EventBoard> list = service.allRead(term);
		JSONArray array = new JSONArray();
		int i = 0;
		for(EventBoard eventBoard : list) {
			JSONObject obj = new JSONObject();
			obj.put("label", i);
			obj.put("value", eventBoard.getTitle());
			i++;
			array.put(obj);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		System.out.println(array);
	
	}
	@RequestMapping(value="/eventBoard/searchPage")
	public String searchPage(@RequestParam(required=false) String title, Model model,Principal principal, Criteria cri)throws Exception{
		String id = principal.getName();
		Paging paging = new Paging();
		paging.setCri(cri);
		paging.setTotalCount(131);
		if(service.searchList(title, cri).isEmpty()) {
			return "redirect:/eventBoard/noPage";
		}
		else {
		
		model.addAttribute("list", mService.read(id));
		model.addAttribute("title", title);
		model.addAttribute("searchList", service.searchList(title, cri));
		model.addAttribute("paging", paging);	
			return "eventBoard/searchPage";
		}
	}
	@GetMapping("/eventBoard/noPage")
	public String noPage(Model model, Principal principal) throws Exception {
		String id = principal.getName();
		model.addAttribute("list", mService.read(id));
		return "eventBoard/noPage";
	}
	
}
