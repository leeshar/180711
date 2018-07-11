package com.icia.service;



import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.vo.Member;

public interface MemberService {
	public void join(Member member, MultipartFile image)throws Exception;
	public String idFind(String irum, String email)throws Exception;
	public String pwdFind(String id, String email)throws Exception;
	public int update(Member member)throws Exception;
	public void resign(String id)throws Exception;
	public Member read(String id)throws Exception;
	public boolean idCheck(String id)throws JsonProcessingException;
}
