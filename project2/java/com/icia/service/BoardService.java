package com.icia.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.icia.vo.Criteria;
import com.icia.vo.Product;

public interface BoardService {
	public List<Product> listCriteria(Criteria cri)throws Exception;
	public int replyCount(int product_id)throws Exception;
	public Product read(int product_id)throws Exception;
	public int updata(Product product, MultipartFile image)throws Exception;
	public void delete(Product product)throws Exception;
	public void write(Product product, MultipartFile image)throws Exception;
}
