package com.myPro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myPro.entity.Board;

public interface BoardRepository {
	// 게시판 글 갯수
	public int bCountAll(String categoriName);
	// 게시판 검색 글 갯수
	public int bSearchCnt(@Param("search") String search,@Param("categoriName") String categoriName);
	// 게시판 페이징 리스트
	public List<Board> bList(@Param("startRow")int startRow, @Param("endRow") int endRow, @Param("categoriName") String categoriName);
	// 글 조회수 증가
	public int bUpReadCnt(Integer bno);
	// 글 추천
	public int bRecommend(String bno);
	// 글 추천 취소
	public int bUnRecommend(String bno);
	// 글 추천 수
	public int bRecommendCnt(int bno);
	// 글 상세내용
	public Map<String, Object> bRead(@Param("bno") Integer bno, @Param("categoriName") String categoriName);
	// 글 쓰기
	public void bWrite(Board board);
	// 글 수정
	public int bUpdate(@Param("title") String title,@Param("content") String content, @Param("bno") String bno);
	// 글 삭제
	public void bDelete(@Param("bno") String bno,@Param("categoriName") String categoriName);
	// 자신의 글 확인
	public String bMyWriteSearch(@Param("bno") String bno, @Param("writer") String writer);
	// 글 댓글 갯수
	public int bUpReplyCnt(int bno);
	// 글 검색
	public List<Board> bBoardSearch(@Param("startRow")int startRow, @Param("endRow") int endRow, @Param("categoriName") String categoriName,@Param("search") String search);
	// 누구의 글
	public String bBoardWriter(String bno);
}
