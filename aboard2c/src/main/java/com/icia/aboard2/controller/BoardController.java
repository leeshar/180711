package com.icia.aboard2.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dao.AttachRepository;
import com.icia.aboard2.dto.BoardDto.CreateBoard;
import com.icia.aboard2.dto.ReplyDto.InsertReply;
import com.icia.aboard2.entity.Board;
import com.icia.aboard2.rest_service.ReplyServiceImpl;
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
	@Autowired
	private ReplyServiceImpl rService;
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
		String str = mapper.writeValueAsString(map);
		
		return str;
	}
	/* 페이지네이션내용
	@GetMapping(value="/boards3",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String listb(Pageable pageable, Model model) throws JsonProcessingException {
		log.info("{}", pageable);
		// map을 만들어 json으로 넘길때 이름으로 값을 뺄 수 있도록 만들어 줬다 
		Map<String, Object> map = new HashMap<>();
		int first = service.list(pageable).indexOf("],")+15;
		int second = service.list(pageable).indexOf("}}")+1;
		
		map.put("records", service.list(pageable).substring(first,second));
		// ObjectMapper로 JSON으로 값을 넘겨준다.
		String str = mapper.writeValueAsString(map);
		return str;
	}*/
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
	
	/* ※ 파일 업로드
	 * 1. 파일의 대분류
	 * 		텍스트 파일 : 	글자를 저장하는 파일. 모든 시스템에서 읽고 쓸 수 있으므로 호환성이 매우 뛰어나다
	 * 						객체를 텍스트로 저장해 호환성을 확보하자 -> HTML, XML, JSON ...
	 * 		이진 파일 : 일반적인 파일로 당연히 텍스트 파일도 이진 파일이지만 보통 둘을 분리해서 생각한다
	 * 					즉 특별한 처리없이 사용할 수 있는 텍스트 파일과 전용 프로그램이 필요한 이진 파일로
	 * 2. content type
	 * 		request 객체에 실어보내는 데이터 형식을 서버에 알려주는 역할
	 * 		웹 프로그래밍에서 프론트와 백엔드가 정보를 주고 받기 위해서는 서로간의 데이터 처리 방식, 데이터 형식을 맞춰야 한다
	 * 		폼의 기본 인코딩 타입인 application/x-www-form-urlencoded
	 * 			key=value&key=value 형식으로 데이터를 전송
	 * 			get 방식은 key=value&key=value가 주소 표시줄을 이용해 전달됨 -> QueryString
	 * 			post 방식은 key=value&key=value가 request body를 이용해 전달됨
	 * 		바이너리 파일을 전달하려면 multipart-formdata 지정 필요
	 * 3. $.ajax
	 * 		contentType
	 * 			boolean 또는 문자열
	 * 			기본값은 'application/x-www-form-urlencoded; charset=UTF-8'
	 * 			만약 data : JSON.stringify(str)로 넘긴다면 contentType: "application/json" 필요
	 * 			false로 주면 contentType을 비운다 (http://api.jquery.com/jquery.ajax/ 참조)
	 * 		processData
	 * 			data를 application/x-www-form-urlencoded 방식으로 처리하는 것이 기본값
	 * 			false로 지정하면 처리작업을 하지 않는다
	 * 4. MultipartFile을 전달받는 방법
	 * 		<input type="file" name="sajin"> --> MultipartFile sajin
	 * 		<input type="file" name="sajin" multiple="multiple"> --> MultipartFile[] sajin
	*/
	
	@PostMapping("/boards/write")
	public String write(@Valid CreateBoard board, BindingResult results, MultipartFile[] files, RedirectAttributes ra) throws BindException, IOException {
		ABoard2Util.throwBindException(results);
		// 원래는 로그인한 아이디를 글쓴이로 저장하면 되는데...각자 알아서 아이디 넣어줄것
		board.setWriter("hasaway11");
		System.out.println(board);
		System.out.println(files);
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
