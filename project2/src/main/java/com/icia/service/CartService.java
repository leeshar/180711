package com.icia.service;

import java.util.List;

import com.icia.vo.Cart;

public interface CartService {
	public void insert(Cart cart)throws Exception;
	public List<?> read(String id)throws Exception;
	public void delete(int cart_id)throws Exception;
	
}
