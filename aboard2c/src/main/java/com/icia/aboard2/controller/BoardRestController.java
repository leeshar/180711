package com.icia.aboard2.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.ReplyDto.DeleteReply;
import com.icia.aboard2.dto.ReplyDto.InsertReply;
import com.icia.aboard2.service.BoardService;
import com.icia.aboard2.service.ReplyServiceImpl;


@RestController
public class BoardRestController {
	@Autowired
	private BoardService service;
	@Autowired
	private ReplyServiceImpl rService;
	@Autowired
	private ObjectMapper mapper;
	// 댓글 작성
	@RequestMapping(value="/boards/reply/insert")
	public ResponseEntity<Void> insert(String reply)throws Exception{
		JSONObject obj = jsonParser(reply);
		InsertReply insert = new InsertReply();
		// 변환 된 객체를 문자열, 정수형 데이터 타입으로 변환한다.
		insert.setBno(Integer.parseInt(obj.get("bno").toString()));
		insert.setId(obj.get("id").toString());
		insert.setReplytext(obj.get("replytext").toString());
		rService.insert(insert);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// 댓글 삭제
	@RequestMapping(value="/boards/reply/delete", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> replyDelete(String reply)throws Exception{
		JSONObject obj = jsonParser(reply);
		String result = rService.delete(obj.get("cno").toString(),obj.get("id").toString());
		return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
		
		
		
	}
	// 댓글 리스트
	@RequestMapping(value="/boards/reply/list", produces = "application/json; charset=UTF-8" )
	public String replyList(@RequestParam int bno)throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("list", rService.list(bno));
		return mapper.writeValueAsString(map);
	}
	// 게시판 사진 업로드
	@RequestMapping("/boards/upload")
	public void multiplePhoto(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String sFileInfo = "";
			String filename = request.getHeader("file-name");
			String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
			filename_ext = filename_ext.toLowerCase();
			String dftFilePath = request.getSession().getServletContext().getRealPath("/");
			String filePath = "/Applications/member/";
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			System.out.println(dftFilePath);
			System.out.println(filePath);
			String realFileNm = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String today = formatter.format(new java.util.Date());
			realFileNm = today+UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));
			String rlFileNm = filePath + realFileNm;
			InputStream is = request.getInputStream();
			OutputStream os = new FileOutputStream(rlFileNm);
			int numRead;
			byte b [] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while((numRead = is.read(b,0,b.length))!= -1) {
				os.write(b, 0, numRead);
			}
			if(is != null) {
				is.close();
			}
			os.flush();
			os.close();
			
			sFileInfo += "&bNewLine=true";
			sFileInfo += "&sFileName="+ filename;
			sFileInfo += "&sFileURL="+"http://localhost:8081/upload/"+realFileNm;
			PrintWriter print = response.getWriter();
			print.print(sFileInfo);
			print.flush();
			print.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	// 글 삭제
	@RequestMapping("/boards/delete")
	public ResponseEntity<String> delete(String board) throws ParseException, JsonProcessingException {
		JSONObject obj = jsonParser(board);
		String result = service.delete(obj.get("bno").toString(),obj.get("id").toString(), obj.get("categoriName").toString());
		return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	};
	// 글 추천
	@RequestMapping("/boards/recommend")
	public ResponseEntity<String> recommend(String recommend) throws ParseException, JsonProcessingException{
		JSONObject obj = jsonParser(recommend);
		String result = service.recommend(obj.get("id").toString(), obj.get("bno").toString());
		return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	// 글 추천 취소
	@RequestMapping("/boards/unrecommend")
	public ResponseEntity<String> unrecommend(String unrecommend) throws ParseException, JsonProcessingException{
		JSONObject obj = jsonParser(unrecommend);
		String result = service.unrecommend(obj.get("id").toString(), obj.get("bno").toString());
		return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
		
	}
	// 글 검색
	@RequestMapping(value="/boards/search/{search}/{page}/{categoriName}", produces = "application/json; charset=UTF-8" )
	public String boardSearch(@PathVariable String search,@PathVariable int page, @PathVariable String categoriName)throws Exception{
		System.out.println(service.boardSearch(page, categoriName, search));
		return mapper.writeValueAsString(service.boardSearch(page, categoriName, search));
	}
	// 중복코드 메서드로
	private JSONObject jsonParser(String unrecommend) throws ParseException {
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(unrecommend);
		return obj;
	}
}
