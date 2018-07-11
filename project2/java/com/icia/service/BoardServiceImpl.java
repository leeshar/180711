package com.icia.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.icia.dao.BoardDao;
import com.icia.exception.BoardNotFoundException;

import com.icia.vo.Criteria;
import com.icia.vo.Product;


@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDao dao;
	
	
	@Value("d:/service/product")
	private String uploadPath;
	@Override
	public List<Product> listCriteria(Criteria cri) throws Exception {
		
		return dao.listCriteria(cri);
	}
	@Transactional
	@Override
	public Product read(int product_id) throws Exception {
		if(product_id==0)
			throw new BoardNotFoundException();
		dao.increase(product_id);
		return dao.read(product_id);
	}

	@Override
	public int updata(Product product, MultipartFile image) throws Exception {
		if(image!=null) {
			String fileName = image.getOriginalFilename();
			File file = new File(uploadPath,fileName);
			try{FileCopyUtils.copy(image.getBytes(), file);}
			catch(Exception e) {
				e.printStackTrace();
			}
			product.setPhoto_url(fileName);
		}
		return dao.updata(product);
		
	}

	@Override
	@PreAuthorize("#product.seller == principal.username or hasRole('ROLE_ADMIN')")
	public void delete(Product product) throws Exception {
		dao.delete(product.getProduct_id());
	}

	@Override
	public void write(Product product, MultipartFile image) throws Exception {
		if(image!=null) {
			String fileName = image.getOriginalFilename();
			File file = new File(uploadPath,fileName);
			try{FileCopyUtils.copy(image.getBytes(), file);}
			catch(Exception e) {
				e.printStackTrace();
			}
			product.setPhoto_url(fileName);
		}
		dao.write(product);
		
	}
	@Override
	public int replyCount(int product_id) throws Exception {
		return dao.replyCount(product_id);
	}

	

}
