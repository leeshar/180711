package com.myPro.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myPro.dao.BoardRepository;
import com.myPro.dao.ReplyRepository;
import com.myPro.dto.ReplyDto.InsertReply;
import com.myPro.dto.ReplyDto.NoticeReply;
import com.myPro.entity.Reply;

@Service
public class ReplyService{
	@Autowired
	private ReplyRepository replyDao;
	@Autowired
	private BoardRepository boardDao;
	@Autowired
	private ModelMapper mapper;
	// rInsert
	@Transactional
	public void rInsert(InsertReply insert) throws Exception {
		Reply reply = mapper.map(insert, Reply.class);
		boardDao.bUpReadCnt(insert.getBno());
		replyDao.rInsert(reply);
	}
	// rDelete
	@Transactional
	public String rDelete(String cno, String id) throws Exception {
		if(replyDao.rIsDelete(cno, id)==null)
			return "본인댓글만 삭제가능합니다";
		replyDao.rDelete(cno);
		return "댓글이 삭제됬습니다";
		
	}
	// rList
	public List<Reply> rList(int bno) throws Exception {
		return replyDao.rList(bno);
	}
	// rNoticeReply
	public void rNoticeReply(NoticeReply notice) throws Exception{
		replyDao.rNoticeReply(notice);
	}
	


}
