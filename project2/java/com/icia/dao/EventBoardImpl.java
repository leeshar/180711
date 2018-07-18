package com.icia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.vo.Criteria;
import com.icia.vo.EventBoard;

@Repository
public class EventBoardImpl implements EventBoardDao{
	@Autowired
	private SqlSession session;
	
	private static String namespace =
	"com.icia.mapper.EventBoardMapper";
	
	
	@Override
	public int count() throws Exception {
		return session.selectOne(namespace+".count");
	}

	@Override
	public List<EventBoard> listCriteria(Criteria cri) throws Exception {
		return session.selectList(namespace+".list", cri);
				
	}

	@Override
	public EventBoard read(int ebno) throws Exception {
		return session.selectOne(namespace+".read", ebno);
	}

	@Override
	public int updata(EventBoard eventBoard) throws Exception {
		return session.update(namespace+".update", eventBoard);
	}

	@Override
	public void delete(int ebno) throws Exception {
		session.delete(namespace+".delete", ebno);
		
	}

	@Override
	public void write(EventBoard eventBoard) throws Exception {
		session.insert(namespace+".write", eventBoard);
		
	}

	@Override
	public void increase(int ebno) throws Exception {
		session.update(namespace+".increase", ebno);
	}

	@Override
	public List<EventBoard> allRead(String title) throws Exception {
		return session.selectList(namespace+".allRead", title);
	}

	@Override
	public List<EventBoard> searchList(Map<String, Object> searchEvent) throws Exception {
		return session.selectList(namespace+".searchList", searchEvent);
	}

	@Override
	public List<Map<String, Object>> photoRead() throws Exception {
		return session.selectList(namespace+".photoRead");
	}

}
