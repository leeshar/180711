package com.icia.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.icia.dao.EventBoardDao;
import com.icia.exception.BoardNotFoundException;
import com.icia.vo.Criteria;
import com.icia.vo.EventBoard;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventBoardServiceImpl implements EventBoardService{
	@Autowired
	private EventBoardDao dao;
	@Value("d:/service/event")
	private String uploadPath;
	
	@Override
	public List<EventBoard> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}
	@Transactional
	@Override
	public EventBoard read(int ebno) throws Exception {
		if(ebno==0)
			throw new BoardNotFoundException();
		dao.increase(ebno);
		return dao.read(ebno);
	}

	@Override
	public int updata(EventBoard eventBoard, MultipartFile image) throws Exception {
		if(image!=null) {
			String fileName = image.getOriginalFilename();
			File file = new File(uploadPath,fileName);
			try{FileCopyUtils.copy(image.getBytes(), file);}
			catch(Exception e) {
				e.printStackTrace();
			}
			eventBoard.setEvent_photo(fileName);
		}
		return dao.updata(eventBoard);
	}

	@Override
	public void delete(EventBoard eventBoard) throws Exception {
		dao.delete(eventBoard.getEbno());
		
	}

	@Override
	public void write(EventBoard eventBoard, MultipartFile image) throws Exception {
		if(image!=null) {
			String fileName = image.getOriginalFilename();
			File file = new File(uploadPath,fileName);
			try{FileCopyUtils.copy(image.getBytes(), file);}
			catch(Exception e) {
				e.printStackTrace();
			}
			eventBoard.setEvent_photo(fileName);
		}
		dao.write(eventBoard);
	}

	@Override
	public List<EventBoard> allRead(String title) throws Exception {
		return dao.allRead(title);
	}

	@Override
	public List<EventBoard> searchList(String title, Criteria cri) throws Exception {
		Map<String, Object> searchEvent = new HashMap<>();
		searchEvent.put("title", title);
		searchEvent.put("page", cri.getPage());
		searchEvent.put("perPageNum", cri.getPerPageNum());
		return dao.searchList(searchEvent);
	}
	@Override
	public Map<String, String> photoRead() throws Exception {
		List<Map<String, Object>> list = dao.photoRead();
		Map<String, String> photo_list = new HashMap<>();
		photo_list.put("first", (String) list.get(0).get("EVENT_PHOTO"));
		photo_list.put("second", (String) list.get(1).get("EVENT_PHOTO"));
		photo_list.put("third", (String) list.get(2).get("EVENT_PHOTO"));
		return photo_list;
		
	
		
	}
	
}
