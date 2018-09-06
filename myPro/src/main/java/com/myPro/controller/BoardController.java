package com.myPro.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myPro.dao.AttachRepository;
import com.myPro.dto.BoardDto.CreateBoard;
import com.myPro.dto.BoardDto.UpdateBoard;
import com.myPro.dto.ReplyDto.InsertReply;
import com.myPro.dto.ReplyDto.NoticeReply;
import com.myPro.service.BoardService;
import com.myPro.service.ReplyService;
import com.myPro.util.ABoard2Contstants;
import com.myPro.util.ABoard2Util;


@RestController
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private AttachRepository attachDao;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ObjectMapper mapper;
	//bList
		@RequestMapping(value="/boards/list/{page}/{categoriName}",produces = "application/json; charset=UTF-8")
		public String bList(@PathVariable int page,@PathVariable String categoriName, Model model) throws JsonProcessingException {
			return mapper.writeValueAsString(boardService.bList(page, categoriName));
	}
	// bRecommend
		@RequestMapping("/boards/recommend")
		public ResponseEntity<String> bRecommend(String recommend) throws ParseException, JsonProcessingException{
			JSONObject obj = jsonParser(recommend);
			String result = boardService.bRecommend(obj.get("id").toString(), obj.get("bno").toString());
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	// bUnRecommend
		@RequestMapping("/boards/unrecommend")
		public ResponseEntity<String> bUnRecommend(String unrecommend) throws ParseException, JsonProcessingException{
			JSONObject obj = jsonParser(unrecommend);
			String result = boardService.bUnRecommend(obj.get("id").toString(), obj.get("bno").toString());
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	//bRead
		@RequestMapping(value="/boards/read/{bno}/{categoriName}",produces = "application/json; charset=UTF-8")
		public String bRead(@PathVariable Integer bno,@PathVariable String categoriName) throws JsonProcessingException {
					Map<String, Object> maps = new HashMap<>();
					Map<String, Object> map = boardService.bRead(bno, categoriName);
					try{
							map.put("savedFileName", attachDao.aList(bno).getSavedFileName());
					}catch(NullPointerException np) {
							map.put("savedFileName", "none.jpg");
					}
					maps.put("board", map);
					return mapper.writeValueAsString(maps);
	}
	// bWrite
		@RequestMapping(value="/boards/write",produces = "application/json; charset=UTF-8")
		public void bWrite(@Valid CreateBoard board, BindingResult results, HttpServletResponse response, RedirectAttributes ra) throws BindException, IOException {
			ABoard2Util.throwBindException(results);
			boolean result = boardService.bWrite(board);
			String categoriName = URLEncoder.encode(board.getCategoriName().toString());
			if(result)
				ra.addFlashAttribute("msg", ABoard2Contstants.WRITE_SUCCESS);
			else
				ra.addFlashAttribute("msg", ABoard2Contstants.JOB_FAIL);
			response.sendRedirect("/#!/boards/list/1/"+categoriName);
	}
	// bUpdate
		@RequestMapping(value="/boards/update",produces = "application/json; charset=UTF-8")
		public void bUpdate(@Valid UpdateBoard update,BindingResult results, RedirectAttributes ra, HttpServletResponse response) throws ParseException, BindException, IOException {
			ABoard2Util.throwBindException(results);
			boolean result = boardService.bUpdate(update);
			// 쿼리스트링을 한글로 인코더해줬다
			String categori = URLEncoder.encode(update.getCategoriName(),"UTF-8");
			if(result)
				ra.addFlashAttribute("msg", ABoard2Contstants.WRITE_SUCCESS);
			else
				ra.addFlashAttribute("msg", ABoard2Contstants.JOB_FAIL);
			response.sendRedirect("/#!/boards/read/"+update.getBno()+"/"+ categori);
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
				String filePath = "d:/service/upload/";
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
	// bDelete
		@RequestMapping("/boards/delete")
		public ResponseEntity<String> bDelete(String board) throws ParseException, JsonProcessingException {
			JSONObject obj = jsonParser(board);
			String result = boardService.bDelete(obj.get("bno").toString(),obj.get("id").toString(), obj.get("categoriName").toString());
			return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	// bBoardSearch
		@RequestMapping(value="/boards/search/{search}/{page}/{categoriName}", produces = "application/json; charset=UTF-8" )
		public String bBoardSearch(@PathVariable String search,@PathVariable int page, @PathVariable String categoriName)throws Exception{
			System.out.println(boardService.bBoardSearch(page, categoriName, search));
			return mapper.writeValueAsString(boardService.bBoardSearch(page, categoriName, search));
	}
	// rInsert
	@RequestMapping(value="/boards/reply/insert")
	public ResponseEntity<Void> rInsert(String reply)throws Exception{
		JSONObject obj = jsonParser(reply);
		InsertReply insert = new InsertReply();
		String bno = obj.get("bno").toString();
		String id = obj.get("id").toString();
		// 변환 된 객체를 문자열, 정수형 데이터 타입으로 변환한다.
		insert.setBno(Integer.parseInt(bno));
		String writer = boardService.bBoardWriter(bno);
		insert.setId(id);
		insert.setReplytext(obj.get("replytext").toString());
		// notice 부분
		NoticeReply notice = new NoticeReply();
		notice.setBno(Integer.parseInt(bno));
		notice.setId(id);
		notice.setNotice_content("새로운 댓글이 달렸습니다");
		notice.setNotice_id(writer);
		// notice 추가
		replyService.rNoticeReply(notice);
		replyService.rInsert(insert);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// rDelete
	@RequestMapping(value="/boards/reply/delete", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> rDelete(String reply)throws Exception{
		JSONObject obj = jsonParser(reply);
		String result = replyService.rDelete(obj.get("cno").toString(),obj.get("id").toString());
		return new ResponseEntity<String>(mapper.writeValueAsString(result),HttpStatus.OK);
	}
	// rList
	@RequestMapping(value="/boards/reply/list", produces = "application/json; charset=UTF-8" )
	public String rList(@RequestParam int bno)throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("list", replyService.rList(bno));
		return mapper.writeValueAsString(map);
	}
	// 중복코드 메서드로
	private JSONObject jsonParser(String unrecommend) throws ParseException {
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(unrecommend);
		return obj;
	}
}
