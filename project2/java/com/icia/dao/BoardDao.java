package com.icia.dao;

import java.util.List;

import com.icia.vo.Criteria;
import com.icia.vo.Product;

public interface BoardDao {
	public int count()throws Exception;
	public int replyCount(int product_id)throws Exception;
	public List<Product> listCriteria(Criteria cri)throws Exception;
	public Product read(int product_id)throws Exception;
	public int updata(Product product)throws Exception;
	public void delete(int product_id)throws Exception;
	public void write(Product product)throws Exception;
	public void increase(int product_id)throws Exception;
	public void like_increase(int product_id)throws Exception;
	public String searchSeller(int product_id)throws Exception;
	public List<?> allRead()throws Exception;
}
