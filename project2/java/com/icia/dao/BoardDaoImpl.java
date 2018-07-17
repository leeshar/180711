package com.icia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.icia.vo.Criteria;
import com.icia.vo.Product;

@Repository
public class BoardDaoImpl implements BoardDao{
	@Autowired
	private SqlSession session;
	private static String namespace =
			"com.icia.mapper.ProductMapper";
	@Override
	public List<Product> listCriteria(Criteria cri) throws Exception {
		return session.selectList(namespace+".list",cri);
	}


	@Override
	public Product read(int product_id) throws Exception {
		return session.selectOne(namespace+".read", product_id);
	}

	@Override
	public int updata(Product product) throws Exception {
		return session.update(namespace+".update", product);
		
	}

	@Override
	public void delete(int product_id) throws Exception {
		session.delete(namespace+".delete", product_id);
	}

	@Override
	public void write(Product product) throws Exception {
		session.insert(namespace+".write", product);
	}

	@Override
	public int count() throws Exception {
		return session.selectOne(namespace+".count");
	}

	@Override
	public void increase(int product_id) throws Exception {
		session.update(namespace+".increase", product_id);
		
	}

	@Override
	public int replyCount(int product_id) {
		return session.selectOne(namespace+".replyCount", product_id);
	}


	@Override
	public void like_increase(int product_id) throws Exception {
		session.update(namespace+".like_increase", product_id);
		
	}


	@Override
	public String searchSeller(int product_id) throws Exception {
		return session.selectOne(namespace+".searchSeller", product_id);
	}


	@Override
	public List<Product> allRead(String product_name) throws Exception {
		return session.selectList(namespace+".allRead", product_name);
	}


	@Override
	@Transactional
	public List<Product> searchList(@Param("criy") Map<String, Object> criy) throws Exception {
		
		return session.selectList(namespace+".searchList",criy);
	}

}
