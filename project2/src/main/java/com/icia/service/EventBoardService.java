package com.icia.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.icia.vo.Criteria;
import com.icia.vo.EventBoard;


public interface EventBoardService {
	public List<EventBoard> listCriteria(Criteria cri)throws Exception;
	public EventBoard read(int ebno)throws Exception;
	public int updata(EventBoard eventBoard, MultipartFile image)throws Exception;
	public void delete(EventBoard eventBoard)throws Exception;
	public void write(EventBoard eventBoard, MultipartFile image)throws Exception;
	public List<EventBoard> allRead(String title)throws Exception;
	public List<EventBoard> searchList(String title, Criteria cri)throws Exception;
	public Map<String, String> photoRead()throws Exception;
}
