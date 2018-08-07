package com.icia.aboard2.rest_service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dao.ReplyDao;
import com.icia.aboard2.dto.ReplyDto.InsertReply;
import com.icia.aboard2.entity.Reply;

@Service
public class ReplyServiceImpl{
	@Autowired
	private ReplyDao dao;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ModelMapper mmapper;
	
	
	public void insert(InsertReply insert) throws Exception {
		Reply reply = mmapper.map(insert, Reply.class);
		dao.insert(reply);
	}

	public void delete(int cno) throws Exception {
		dao.delete(cno);
		
	}


	public List<Reply> list(int bno) throws Exception {
		return dao.list(bno);
	}

	


}
