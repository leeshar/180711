package com.icia.aboard2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.ReplyDto.InsertReply;
import com.icia.aboard2.entity.Attachment;
import com.icia.aboard2.service.BoardService;
import com.icia.aboard2.service.ReplyServiceImpl;
import com.icia.aboard2.util.ABoard2Contstants;
import com.icia.aboard2.util.MediaUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BoardRestController {
	@Autowired
	private BoardService service;
	@Autowired
	private ReplyServiceImpl rService;
	@Autowired
	private ObjectMapper mapper;
	@GetMapping("/board/displayFile")
	public ResponseEntity<byte[]> displayFile(int bno, int ano) throws IOException {
		Attachment attachment = service.getAttachment(bno, ano);
		// 원본 파일의 이름 저장
		String originalFileName = attachment.getOriginalFileName();
		// response에 파일을 담아 보내기 위한 ResponseEntity<byte[]> 객체 생성
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;
		File dest = null;
		try {
			File src = new File(ABoard2Contstants.UPLOAD_PATH, attachment.getSavedFileName());
			dest = new File(ABoard2Contstants.UPLOAD_PATH, originalFileName);
			FileCopyUtils.copy(src, dest);
			MediaType mType = MediaUtils.getMediaType(originalFileName.substring(originalFileName.lastIndexOf(".")+1).toUpperCase());
			HttpHeaders headers = new HttpHeaders();
			if(mType!=null) {
				headers.setContentType(mType);
				headers.add("Content-Disposition", "inline;filename=" + originalFileName +";");
			}
			else {
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment;filename=" + originalFileName +";");
			}
			in = new FileInputStream(dest);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch(IOException e) {
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
			if(dest.exists())
				dest.delete();
		}
		return entity; 
	}
	// 댓글 작성
	@RequestMapping(value="/boards/reply/insert")
	public ResponseEntity<Void> insert(String reply)throws Exception{
		// JSONParser로 Object로 변환시켜준다.
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(reply);
		InsertReply insert = new InsertReply();
		// 변환 된 객체를 문자열, 정수형 데이터 타입으로 변환한다.
		insert.setBno(Integer.parseInt(obj.get("bno").toString()));
		insert.setId(obj.get("id").toString());
		insert.setReplytext(obj.get("replytext").toString());
		rService.insert(insert);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// 댓글 리스트
	@RequestMapping(value="/boards/reply/list", produces = "application/json; charset=UTF-8" )
	public String replyList(@RequestParam int bno)throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("list", rService.list(bno));
		String str = mapper.writeValueAsString(map);
		log.info("{}", str);
		return str;
		
	}
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
}
