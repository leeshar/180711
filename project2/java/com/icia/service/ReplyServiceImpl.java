package com.icia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.dao.ReplyDao;
import com.icia.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyDao dao;
	
	
	@Override
	public void insert(Reply reply) throws Exception {
		dao.insert(reply);
	}

	@Override
	public void delete(int cno) throws Exception {
		dao.delete(cno);
		
	}

	@Override
	public void update(Reply reply) throws Exception {
		dao.update(reply);
		
	}

	@Override
	public List<Reply> list(int product_id) throws Exception {
		return dao.list(product_id);
	}

	@Override
	public void eBoardinsert(Reply reply) throws Exception {
		dao.eBoardinsert(reply);
		
	}

	@Override
	public void eBoarddelete(int cno) throws Exception {
		dao.eBoarddelete(cno);
	}

	@Override
	public void eBoardupdate(Reply reply) throws Exception {
		dao.eBoardupdate(reply);
	}

	@Override
	public List<Reply> eBoardlist(int ebno) throws Exception {
		return dao.eBoardlist(ebno);
	}

}
