package com.icia.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.dao.MemberDao;
import com.icia.vo.Member;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao dao;
	@Autowired
	private PasswordEncoder encoder;
	@Value("/Applications/member")
	private String uploadPath;
	
	@Override
	
	public void join(Member member, MultipartFile image) throws Exception {
		String encodedPwd = encoder.encode(member.getPwd());
		member.setPwd(encodedPwd);
		if(image!=null) {
			String fileName = image.getOriginalFilename();
			File file = new File(uploadPath,fileName);
			try{FileCopyUtils.copy(image.getBytes(), file);}
			catch(Exception e) {
				e.printStackTrace();
			}
			member.setPhoto(fileName);
		}
	
		dao.join(member);
	}

	@Override
	public String idFind(String irum,String email) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("irum", irum);
		map.put("email", email);
		return dao.idFind(map);
	}

	@Override
	public String pwdFind(String id, String email) throws Exception {
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("email", email);
		return dao.pwdFind(map);
	}

	@Override
	public int update(Member member) throws Exception {
		return dao.update(member);
	}

	@Override
	public void resign(String id) throws Exception {
		dao.resign(id);
		
	}

	@Override
	public Member read(String id) throws Exception {
		return dao.read(id);
	}

	@Override
	public boolean idCheck(String id) throws JsonProcessingException {
		
		log.info("{}", dao.idCheck(id));
		if(dao.idCheck(id)==null) {
			return true;
		}
		else
			return false;
	}

	

	

}
