package com.icia.aboard2.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dao.AttachRepository;
import com.icia.aboard2.dto.BoardDto.CreateBoard;
import com.icia.aboard2.entity.Board;
import com.icia.aboard2.service.BoardService;
import com.icia.aboard2.util.ABoard2Contstants;
import com.icia.aboard2.util.ABoard2Util;
import com.icia.aboard2.util.pagination.Pageable;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private AttachRepository aDao;
	//REST방식으로 list를 보여준다.
	@GetMapping(value="/boards/list/{page}",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String lista(@PathVariable int page, Model model) throws JsonProcessingException {
		Pageable pageable = new Pageable();
		pageable.setPage(page);
		log.info("{}", pageable);
		// 값을 빼서 넣어주기 위해 indexOf를 사용해 값을 잘라줘서 map에 담아 전달한다.
		int first = service.list(pageable).indexOf("[");
		int second = service.list(pageable).indexOf("]")+1;
		Map<String, Object> map = new HashMap<>();
		map.put("records", service.list(pageable).substring(first,second));
		log.info("{}",map);
		String str = mapper.writeValueAsString(map);
		
		return str;
	}
	@GetMapping(value="/boards/listAll",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String listAll() throws Exception{
		Map<String, Object> map = new HashMap<>();
		
		map.put("listAll", service.listAll());
		log.info("{}", map);
		String listAll = mapper.writeValueAsString(map);
		return listAll;
	}
	// Rest 방식으로  GET 해줬다.
	@GetMapping(value="/boards/read/{bno}",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String read(@PathVariable Integer bno) throws JsonProcessingException {
		Map<String, Object> maps = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		
		System.out.println(aDao.list(bno));
		// 저장된 사진이 없으니까 read를 했을 때 null이 뜬다.
		// attach에 null 이 담겼을 때 이미지 처리를 해준다.
		if(aDao.list(bno).getOriginalFileName()!=null) {
		map.put("savedFileName", aDao.list(bno).getSavedFileName());	
		}
		if(aDao.list(bno).getOriginalFileName()==null) {
			map.put("savedFileName", "none.jpg");
		}
		// b 객체를 생성해서 담아준 뒤 값을 빼서 map으로 집어넣었다.
		// service.read(bno).get으로 뺐더니 너무 많이 실행됬다.
		// 그래서 조회수가 한번에 8씩 증가했다.
		// b를 만들어줘서 해결 할 수 있었다.
		Board b = service.read(bno);
		map.put("bno", b.getBno());
		map.put("title", b.getTitle());
		map.put("content", b.getContent());
		map.put("writer", b.getWriter());
		map.put("writeDate",b.getWriteDate());
		map.put("readCnt", b.getReadCnt());
		map.put("recommendCnt", b.getRecommendCnt());
		map.put("reportCnt", b.getReportCnt());
		maps.put("board", map);
		String str = mapper.writeValueAsString(maps);
		return str;
	}
	
	
	@GetMapping("/boards/write")
	public String write() {
		return "boards/write";
	}
	@PostMapping("/boards/write")
	public String write(@Valid CreateBoard board, BindingResult results, MultipartFile[] files, RedirectAttributes ra) throws BindException, IOException {
		ABoard2Util.throwBindException(results);
		boolean result = service.write(board, files);
		if(result)
			ra.addFlashAttribute("msg", ABoard2Contstants.WRITE_SUCCESS);
		else
			ra.addFlashAttribute("msg", ABoard2Contstants.JOB_FAIL);
		return "redirect:/#!/boards/list/1";
	}
	@GetMapping("/boards/delete")
	public String delete(@RequestParam int bno) {
		service.delete(bno);
		return "redirect:/";
	}
	
	
	
}
