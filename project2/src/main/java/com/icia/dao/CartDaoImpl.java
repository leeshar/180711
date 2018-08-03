package com.icia.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.vo.Cart;

@Repository
public class CartDaoImpl<E> implements CartDao{
	@Autowired
	private SqlSession session;
	
	private static String namespace
	="com.icia.mapper.CartMapper";
	
	@Override
	public void insert(Cart cart) throws Exception {
		session.insert(namespace+".insert", cart);
	}

	@Override
	public List<?> read(String id) throws Exception {
		return session.selectList(namespace+".read",id);
	}

	@Override
	public void delete(int cart_id) throws Exception {
		session.delete(namespace+".delete", cart_id);
		
	}



}
