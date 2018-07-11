package com.icia.dao;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.vo.Member;


public interface MemberDao {
	public void join(Member member)throws Exception;
	public String idFind(Map<String, String> map)throws Exception;
	public String pwdFind(Map<String, String> map)throws Exception;
	public int update(Member member)throws Exception;
	public void resign(String id)throws Exception;
	public Member read(String id)throws Exception;
	public String idCheck(String id)throws JsonProcessingException;
	
}
