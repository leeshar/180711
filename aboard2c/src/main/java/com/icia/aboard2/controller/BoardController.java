package com.icia.aboard2.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dao.AttachRepository;
import com.icia.aboard2.dto.BoardDto.CreateBoard;
import com.icia.aboard2.dto.BoardDto.UpdateBoard;
import com.icia.aboard2.service.BoardService;
import com.icia.aboard2.util.ABoard2Contstants;
import com.icia.aboard2.util.ABoard2Util;

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
	@GetMapping(value="/boards/list/{page}/{categoriName}",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String lista(@PathVariable int page,@PathVariable String categoriName, Model model) throws JsonProcessingException {
		String str = mapper.writeValueAsString(service.list(page, categoriName));
		return str;
	}
	@GetMapping(value="/boards/listAll",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String listAll() throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("listAll", service.listAll());
		String listAll = mapper.writeValueAsString(map);
		return listAll;
	}
	// Rest 방식으로  GET 해줬다.
	@GetMapping(value="/boards/read/{bno}/{categoriName}",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String read(@PathVariable Integer bno,@PathVariable String categoriName) throws JsonProcessingException {
		Map<String, Object> maps = new HashMap<>();
		Map<String, Object> map = service.read(bno, categoriName);
			
		try{
				map.put("savedFileName", aDao.list(bno).getSavedFileName());
		}catch(NullPointerException np) {
				map.put("savedFileName", "none.jpg");
		}
		maps.put("board", map);
		String str = mapper.writeValueAsString(maps);
		return str;
	}
	@PostMapping("/boards/write")
	public String write(@Valid CreateBoard board, BindingResult results, RedirectAttributes ra) throws BindException, IOException {
		ABoard2Util.throwBindException(results);
		boolean result = service.write(board);
		if(result)
			ra.addFlashAttribute("msg", ABoard2Contstants.WRITE_SUCCESS);
		else
			ra.addFlashAttribute("msg", ABoard2Contstants.JOB_FAIL);
		return "redirect:/#!/boards/list/1";
	}
	@PostMapping("/boards/update")
	public String update(@Valid UpdateBoard update,BindingResult results, RedirectAttributes ra) throws ParseException, JsonProcessingException, BindException, UnsupportedEncodingException {
		ABoard2Util.throwBindException(results);
		boolean result = service.update(update);
		// 쿼리스트링을 한글로 인코더해줬다
		String categori = URLEncoder.encode(update.getCategoriName(),"UTF-8");
		if(result)
			ra.addFlashAttribute("msg", ABoard2Contstants.WRITE_SUCCESS);
		else
			ra.addFlashAttribute("msg", ABoard2Contstants.JOB_FAIL);
		return "redirect:/#!/boards/read/"+update.getBno()+"/"+ categori;
	}
	
}
