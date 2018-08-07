package com.icia.aboard2.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.ReplyDto.InsertReply;
import com.icia.aboard2.entity.Attachment;
import com.icia.aboard2.entity.Reply;
import com.icia.aboard2.rest_service.BoardRestService;
import com.icia.aboard2.rest_service.ReplyServiceImpl;
import com.icia.aboard2.util.ABoard2Contstants;
import com.icia.aboard2.util.MediaUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BoardRestController {
	@Autowired
	private BoardRestService service;
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
			// abc.jpg로 업로드했고 저장된 파일 이름은 11223344-abc.jpg라고 하자
			//		왜 원본 이름으로 저장하지 않는가? 파일 이름이 겹칠 수 있으니까
			// 사용자가 abc.jpg 파일을 보겠다고 한 경우 11223344-abc.jpg를 abc.jpg로 복사한 다음 다운로드하고 abc.jpg는 삭제한다
			
			// 11223344-abc.jpg 파일을 연다
			File src = new File(ABoard2Contstants.UPLOAD_PATH, attachment.getSavedFileName());
			// abc.jpg 파일을 생성한다
			dest = new File(ABoard2Contstants.UPLOAD_PATH, originalFileName);
			// 11223344-abc.jpg를 abc.jpg로 복사한다
			FileCopyUtils.copy(src, dest);
			// 파일이 이미지인지 아닌지 판단 : abc.jpg의 jpg를 잘라낸 다음 대문자로 바꿔 MediaUtil을 이용해 판단
			MediaType mType = MediaUtils.getMediaType(originalFileName.substring(originalFileName.lastIndexOf(".")+1).toUpperCase());
			
			// 응답을 위한 헤더를 생성
			// 헤더에 contentType을 지정 -> 다운로드한 브라우저가 자신이 처리가능한 contentType인 경우 처리, 불가능한 경우 다운로드
			HttpHeaders headers = new HttpHeaders();
			// setContentType으로 파일의 mediaType을 지정한다.
			// Content-Disposition 은 컨텐트 타입의 옵션으로 파일 처리방법과 파일명을 지정한다
			if(mType!=null) {
				// 이미지일 경우 Content-Disposition을 inline으로 지정해 브라우저가 처리하도록 함. 처리못할 경우 다운로드
				headers.setContentType(mType);
				headers.add("Content-Disposition", "inline;filename=" + originalFileName +";");
			}
			else {
				// 이미지가 아닐 경우 무조건 다운로드
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
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(reply);
		InsertReply insert = new InsertReply();
		int bno = Integer.parseInt(obj.get("bno").toString());
		String id = obj.get("id").toString();
		String replytext = obj.get("replytext").toString();
		insert.setBno(bno);
		insert.setId(id);
		insert.setReplytext(replytext);
	
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
}
