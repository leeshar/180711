package com.icia.aboard2.dao;

import java.util.List;

import com.icia.aboard2.entity.Reply;


public interface ReplyDao {
	public void insert(Reply reply)throws Exception;
	
	public void delete(int cno)throws Exception;
	
	public void update(Reply reply)throws Exception;
	
	public List<Reply> list(int bno)throws Exception;

}
