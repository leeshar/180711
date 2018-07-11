package com.icia.service;

import java.util.List;

import com.icia.vo.Reply;

public interface ReplyService {
	public void insert(Reply reply)throws Exception;
	public void delete(int cno)throws Exception;
	public void update(Reply reply)throws Exception;
	public List<Reply> list(int product_id)throws Exception;
}
