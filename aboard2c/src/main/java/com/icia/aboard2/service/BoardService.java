package com.icia.aboard2.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.icia.aboard2.dao.AttachRepository;
import com.icia.aboard2.dao.BoardRepository;
import com.icia.aboard2.dto.BoardDto;
import com.icia.aboard2.dto.BoardDto.CreateBoard;
import com.icia.aboard2.entity.Attachment;
import com.icia.aboard2.entity.Board;
import com.icia.aboard2.exception.BoardNotFoundException;
import com.icia.aboard2.util.ABoard2Contstants;
import com.icia.aboard2.util.pagination.Page;
import com.icia.aboard2.util.pagination.Pageable;
import com.icia.aboard2.util.pagination.Pagination;
import com.icia.aboard2.util.pagination.PagingUtil;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardDao;
	@Autowired
	private AttachRepository attachDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Gson gson;
	
	public String listAll(){
		return gson.toJson(boardDao.listAll());
	}
	
	
	@Transactional
	public boolean write(CreateBoard create, MultipartFile[] files)  {
		Board board = modelMapper.map(create, Board.class);
		boardDao.write(board);
		// files는 파일 업로드를 안해도 length가 1, 파일 하나 업로드해도 length가 1이다
		// files에 대한 null 체크 불필요
		for(MultipartFile file:files) {
			// 원본 파일 이름 가져오기
			String originalFileName = file.getOriginalFilename();
			// 현재 시간을 앞에 붙여서 저장할 파일 이름으로 변경하기
			String savedFileName = System.currentTimeMillis() + "-" + originalFileName;
			// 파일을 업로드하면 서버의 메모리로 업로드되므로 하드디스크에 저장해야 한다
			// 저장하기 위해 비어있는 파일을 생성한 다음 메모리의 원본을 복사
			File dest = new File(ABoard2Contstants.UPLOAD_PATH, savedFileName);
			try {
				FileCopyUtils.copy(file.getBytes(), dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// attach 테이블에 첨부파일 정보를 저장
			// attach(ano, originalFileName, savedFileName, 글번호)
			Attachment attachment = new Attachment(0, originalFileName, savedFileName, board.getBno());
			attachDao.insert(attachment);
		}
		return true;
	}

	@Transactional
	public Map<String, Object> read(Integer bno, String categoriName) {
		int result = boardDao.increaseReadCnt(bno);
		if(result==0)
			throw new BoardNotFoundException();
		
		
		return boardDao.read(bno,categoriName);
	}

	public String list(Pageable pageable,String categoriName) {
		Integer count = boardDao.count();
		Pagination pagination = PagingUtil.getPagination(pageable, count);
		List list = boardDao.list(pagination.getStartRow(), pagination.getEndRow(),categoriName);
		Page page = Page.builder().list(list).pagination(pagination).build();
		return gson.toJson(page);
	}
	public void delete(int bno) {
		boardDao.delete(bno);
	}
}



