package com.icia.dao;

import java.util.List;

import com.icia.vo.Cart;

public interface CartDao {
	public void insert(Cart cart)throws Exception;
	public List<?> read(String id)throws Exception;
	public void delete(int cart_id)throws Exception;
	
}
