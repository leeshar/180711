package com.myPro.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.myPro.dao.AttachRepository;
import com.myPro.dao.BoardRepository;
import com.myPro.dao.UserRepository;
import com.myPro.dto.BoardDto.CreateBoard;
import com.myPro.dto.BoardDto.UpdateBoard;
import com.myPro.entity.Attachment;
import com.myPro.entity.Board;
import com.myPro.exception.BoardNotFoundException;
import com.myPro.pagination.Pageable;
import com.myPro.pagination.Pagination;
import com.myPro.pagination.PagingUtil;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private AttachRepository attachDao;
	@Autowired
	private ModelMapper modelMapper;
	
	// bList
	public Map<String, Object> bList(int page,String categoriName) {
		Pageable pageable = new Pageable();
		pageable.setPage(page);
		Integer count = boardDao.bCountAll(categoriName);
		Map<String, Object> map = new HashMap<>();
		Pagination pagination = PagingUtil.getPagination(pageable, count);
		// list 에는 페이징 리스트를 넣고
		map.put("list", boardDao.bList(pagination.getStartRow(), pagination.getEndRow(), categoriName));
		// pagination 에는 페이지네이션을 넣어준다
		map.put("pagination", pagination);
		return map;
	}
	// bRecommend
		public String bRecommend(String id, String bno) {
			String writer = id;
			if(boardDao.bMyWriteSearch(bno, writer)!=null)
				return "NO";
			boardDao.bRecommend(bno);
			return "OK";
					
		}
		// bUnRecommend
		public String bUnRecommend(String id, String bno) {
			boardDao.bUnRecommend(bno);
			return "OK";
					
		}
	// bRead
		@Transactional
		public Map<String, Object> bRead(Integer bno, String categoriName) {
			int result = boardDao.bUpReadCnt(bno);
			if(result==0)
				throw new BoardNotFoundException();
			
			
			return boardDao.bRead(bno,categoriName);
		}
	// bWrite
	public boolean bWrite(CreateBoard create)  {
		Board board = modelMapper.map(create, Board.class);
		boardDao.bWrite(board);
		return true;
	}
	// bUpdate
	public boolean bUpdate(UpdateBoard update) {
	System.out.println(boardDao.bUpdate(update.getTitle(), update.getContent(), update.getBno()));
	boardDao.bUpdate(update.getTitle(), update.getContent(), update.getBno());
	return true;	
		
		
	}
	// bDelete
	public String bDelete(String bno,String id, String categoriName) {
		String writer = id;
		System.out.println(boardDao.bMyWriteSearch(bno,writer));
			if(boardDao.bMyWriteSearch(bno,writer)!=null) { 
				boardDao.bDelete(bno,categoriName);
				return "YES";
			}
			if(userDao.uAuthorities(id)=="ROLE_ADMIN") {
				boardDao.bDelete(bno, categoriName);
				return "YES";
			}
				
		return "NO";
	}
	// aGetAttachment
	public Attachment aGetAttachment(int bno, int ano) throws FileNotFoundException {
		Attachment attachment = attachDao.aGetAttachment(bno, ano);
		if(attachment==null)
			throw new FileNotFoundException("원본 파일을 찾을 수 없습니다 ");
		return attachment;
	}
	// bBoardSearch
	public Map<String, Object> bBoardSearch(int page,String categoriName,String search){
		Pageable pageable = new Pageable();
		pageable.setPage(page);
		Integer count = boardDao.bSearchCnt(search, categoriName);
		Map<String, Object> map = new HashMap<>();
		if(count==0) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("boardSearch", "NO");
			return maps;
		}
		
		Pagination pagination = PagingUtil.getPagination(pageable, count);
		// list 에는 페이징 리스트를 넣고
		map.put("boardSearch", boardDao.bBoardSearch(pagination.getStartRow(), pagination.getEndRow(), categoriName,search));
		// pagination 에는 페이지네이션을 넣어준다
		map.put("pagination", pagination);
		return map;
	}
	// bBoardWriter
	public String bBoardWriter(String bno) {
		return boardDao.bBoardWriter(bno);
	}
}



