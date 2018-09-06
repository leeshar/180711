package com.myPro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myPro.dto.ReplyDto.NoticeReply;
import com.myPro.entity.Reply;


public interface ReplyRepository {
	// 댓글 추가
	public void rInsert(Reply reply)throws Exception;
	// 댓글 삭제
	public int rDelete(String cno)throws Exception;
	// 댓글 아이디 확인
	public String rIsDelete(@Param("cno") String cno,@Param("id") String id)throws Exception;
	// 댓글 리스트
	public List<Reply> rList(int bno)throws Exception;
	// 댓글 알림
	public void rNoticeReply(NoticeReply notice)throws Exception;
	
}
