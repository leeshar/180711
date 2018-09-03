package com.icia.aboard2.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icia.aboard2.dao.BoardRepository;
import com.icia.aboard2.dao.ReplyRepository;
import com.icia.aboard2.dto.ReplyDto.InsertReply;
import com.icia.aboard2.dto.ReplyDto.NoticeReply;
import com.icia.aboard2.entity.Reply;

@Service
public class ReplyServiceImpl{
	@Autowired
	private ReplyRepository dao;
	@Autowired
	private BoardRepository bDao;
	@Autowired
	private ModelMapper mapper;
	// 댓글쓰기
	@Transactional
	public void insert(InsertReply insert) throws Exception {
		Reply reply = mapper.map(insert, Reply.class);
		bDao.upReplyCnt(insert.getBno());
		dao.insert(reply);
	}
	// 댓글 알림
	public void replyNotice(NoticeReply notice) throws Exception{
		dao.noticeReply(notice);
	}
	
	// 댓글삭제
	@Transactional
	public String delete(String cno, String id) throws Exception {
		if(dao.isDelete(cno, id)==null)
			return "본인댓글만 삭제가능합니다";
		dao.delete(cno);
		return "댓글이 삭제됬습니다";
		
	}

	// 댓글리스트
	public List<Reply> list(int bno) throws Exception {
		return dao.list(bno);
	}

	


}
