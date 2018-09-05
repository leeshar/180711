package com.icia.aboard2.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.icia.aboard2.dao.AttachRepository;
import com.icia.aboard2.dao.BoardRepository;
import com.icia.aboard2.dao.UserRepository;
import com.icia.aboard2.dto.BoardDto.CreateBoard;
import com.icia.aboard2.dto.BoardDto.UpdateBoard;
import com.icia.aboard2.entity.Attachment;
import com.icia.aboard2.entity.Board;
import com.icia.aboard2.exception.BoardNotFoundException;
import com.icia.aboard2.util.pagination.Pageable;
import com.icia.aboard2.util.pagination.Pagination;
import com.icia.aboard2.util.pagination.PagingUtil;

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
	
	//전체리스트
	public List<Board> listAll(){
		return boardDao.listAll();
	}
	// 리스트
	public Map<String, Object> list(int page,String categoriName) {
		Pageable pageable = new Pageable();
		pageable.setPage(page);
		Integer count = boardDao.count(categoriName);
		Map<String, Object> map = new HashMap<>();
		Pagination pagination = PagingUtil.getPagination(pageable, count);
		// list 에는 페이징 리스트를 넣고
		map.put("list", boardDao.list(pagination.getStartRow(), pagination.getEndRow(), categoriName));
		// pagination 에는 페이지네이션을 넣어준다
		map.put("pagination", pagination);
		return map;
	}
	// 글쓰기
	public boolean write(CreateBoard create)  {
		Board board = modelMapper.map(create, Board.class);
		boardDao.write(board);
		return true;
	}
	// 글수정
	public boolean update(UpdateBoard update) {
	System.out.println(boardDao.update(update.getTitle(), update.getContent(), update.getBno()));
	boardDao.update(update.getTitle(), update.getContent(), update.getBno());
	return true;	
		
		
	}
	// 글읽기
	@Transactional
	public Map<String, Object> read(Integer bno, String categoriName) {
		int result = boardDao.increaseReadCnt(bno);
		if(result==0)
			throw new BoardNotFoundException();
		
		
		return boardDao.read(bno,categoriName);
	}
	// 글삭제
	public String delete(String bno,String id, String categoriName) {
		String writer = id;
		System.out.println(boardDao.postSearch(bno,writer));
			if(boardDao.postSearch(bno,writer)!=null) { 
				boardDao.delete(bno,categoriName);
				return "YES";
			}
			if(userDao.authoritySearch(id)=="ROLE_ADMIN") {
				boardDao.delete(bno, categoriName);
				return "YES";
			}
				
		return "NO";
	}
	// 추천
	public String recommend(String id, String bno) {
		String writer = id;
		if(boardDao.postSearch(bno, writer)!=null)
			return "NO";
		boardDao.recommend(bno);
		return "OK";
				
	}
	// 추천취소
	public String unrecommend(String id, String bno) {
		boardDao.unrecommend(bno);
		return "OK";
				
	}
	// 신고
	public int report(String id, int bno, boolean alreadyReport) {
		int result = boardDao.report(bno);
		if(result==0)
			throw new BoardNotFoundException();
		return boardDao.getReportCnt(bno);
	}
	// 파일
	public Attachment getAttachment(int bno, int ano) throws FileNotFoundException {
		Attachment attachment = attachDao.getAttachment(bno, ano);
		if(attachment==null)
			throw new FileNotFoundException("원본 파일을 찾을 수 없습니다 ");
		return attachment;
	}
	// 검색
	public Map<String, Object> boardSearch(int page,String categoriName,String search){
		Pageable pageable = new Pageable();
		pageable.setPage(page);
		Integer count = boardDao.searchCount(search, categoriName);
		Map<String, Object> map = new HashMap<>();
		if(count==0) {
			Map<String, Object> maps = new HashMap<>();
			maps.put("boardSearch", "NO");
			return maps;
		}
		
		Pagination pagination = PagingUtil.getPagination(pageable, count);
		// list 에는 페이징 리스트를 넣고
		map.put("boardSearch", boardDao.boardSearch(pagination.getStartRow(), pagination.getEndRow(), categoriName,search));
		// pagination 에는 페이지네이션을 넣어준다
		map.put("pagination", pagination);
		return map;
	}
	// 누구의 글
	public String whoWriter(String bno) {
		return boardDao.whoWriter(bno);
	}
}


