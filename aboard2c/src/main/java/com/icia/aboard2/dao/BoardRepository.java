package com.icia.aboard2.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.icia.aboard2.entity.*;

public interface BoardRepository {
	public int count(String categoriName);
	
	public List<Board> listAll();
	
	public List<Board> list(@Param("startRow")int startRow, @Param("endRow") int endRow, @Param("categoriName") String categoriName);

	public int increaseReadCnt(Integer bno);

	public Map<String, Object> read(@Param("bno") Integer bno, @Param("categoriName") String categoriName);
	
	public int recommend(int bno);
	
	public int getRecommendCnt(int bno);

	public int report(int bno);

	public int getReportCnt(int bno);

	public void write(Board board);

	public int update(Board board);

	public void delete(int bno);
	
	public int upReplyCnt(int bno);
}
