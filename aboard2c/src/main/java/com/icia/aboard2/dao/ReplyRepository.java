package com.icia.aboard2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icia.aboard2.entity.Reply;


public interface ReplyRepository {
	// 댓글 추가
	public void insert(Reply reply)throws Exception;
	// 댓글 삭제
	public int delete(String cno)throws Exception;
	// 댓글 아이디 확인
	public String isDelete(@Param("cno") String cno,@Param("id") String id)throws Exception;
	// 댓글 수정
	public void update(Reply reply)throws Exception;
	// 댓글 리스트
	public List<Reply> list(int bno)throws Exception;

}
