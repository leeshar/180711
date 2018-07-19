package com.icia.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.vo.Reply;

@Repository
public class ReplyDaoImpl implements ReplyDao{
	@Autowired
	private SqlSession session;
	
	private static String namespace
	="com.icia.mapper.ReplyMapper";
	
	
	@Override
	public void insert(Reply reply) throws Exception {
		session.insert(namespace+".insert", reply);
		
	}

	@Override
	public void delete(int cno) throws Exception {
		session.delete(namespace+".delete", cno);
	}

	@Override
	public void update(Reply reply) throws Exception {
		session.update(namespace+".update", reply);
	}

	@Override
	public List<Reply> list(int product_id) throws Exception {
		return session.selectList(namespace+".list",product_id);
	}
	@Override
	public void eBoardinsert(Reply reply) throws Exception {
		session.insert(namespace+".eBoardinsert", reply);
		
	}

	@Override
	public void eBoarddelete(int cno) throws Exception {
		session.delete(namespace+".eBoarddelete", cno);
	}

	@Override
	public void eBoardupdate(Reply reply) throws Exception {
		session.update(namespace+".eBoardupdate", reply);
	}

	@Override
	public List<Reply> eBoardlist(int ebno) throws Exception {
		return session.selectList(namespace+".eBoardlist", ebno);
	}
	
}
