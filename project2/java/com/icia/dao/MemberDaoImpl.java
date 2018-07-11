package com.icia.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDaoImpl implements MemberDao{
	@Autowired
	private SqlSession session;
	
	private static String namespace
	= "com.icia.mapper.MemberMapper";
	
	
	@Override
	public void join(Member member) throws Exception {
		session.insert(namespace+".authorities", member.getId());
		session.insert(namespace+".join", member);
		
	}

	@Override
	public String idFind(Map<String, String> map) throws Exception {
		return session.selectOne(namespace+".idFind", map);
		
	}

	@Override
	public String pwdFind(Map<String, String> map) throws Exception {
		return session.selectOne(namespace+".pwdFind", map);
	}

	@Override
	public int update(Member member) throws Exception {
		return session.update(namespace+".update", member);
		
	}

	@Override
	public void resign(String id) throws Exception {
		session.delete(namespace+".resign", id);
	}

	@Override
	public Member read(String id) throws Exception {
		return session.selectOne(namespace+".read", id);
	}

	@Override
	public String idCheck(String id) throws JsonProcessingException {
		log.info("{}",id);
		return session.selectOne(namespace+".idCheck", id);
	}
	
}
