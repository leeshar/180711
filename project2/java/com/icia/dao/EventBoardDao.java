package com.icia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icia.vo.Criteria;
import com.icia.vo.EventBoard;


public interface EventBoardDao {
	public int count()throws Exception;
	public List<EventBoard> listCriteria(Criteria cri)throws Exception;
	public EventBoard read(int ebno)throws Exception;
	public int updata(EventBoard eventBoard)throws Exception;
	public void delete(int ebno)throws Exception;
	public void write(EventBoard eventBoard)throws Exception;
	public void increase(int ebno)throws Exception;
	public List<EventBoard> allRead(String title)throws Exception;
	public List<EventBoard> searchList(@Param("event") Map<String, Object> searchEvent)throws Exception;
	public List<Map<String, Object>> photoRead()throws Exception;
}
