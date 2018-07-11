package com.icia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.dao.CartDao;
import com.icia.vo.Cart;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService{
	@Autowired
	private CartDao dao;

	@Override
	public void insert(Cart cart) throws Exception {
		dao.insert(cart);
		
	}

	@Override
	public List<?> read(String id) throws Exception {
		log.info("{}", dao.read(id));
		return dao.read(id);
	}

	@Override
	public void delete(int cart_id) throws Exception {
		dao.delete(cart_id);
	}
	
	
	
}
