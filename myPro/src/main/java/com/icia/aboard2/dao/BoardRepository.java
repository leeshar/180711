package com.icia.aboard2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icia.aboard2.entity.Board;

public interface BoardRepository {
	// 게시판 글 갯수
	public int count(String categoriName);
	// 게시판 검색 글 갯수
	public int searchCount(@Param("search") String search,@Param("categoriName") String categoriName);
	// 게시판 전체 글
	public List<Board> listAll();
	// 게시판 페이징 리스트
	public List<Board> list(@Param("startRow")int startRow, @Param("endRow") int endRow, @Param("categoriName") String categoriName);
	// 글 조회수 증가
	public int increaseReadCnt(Integer bno);
	// 글 상세내용
	public Map<String, Object> read(@Param("bno") Integer bno, @Param("categoriName") String categoriName);
	// 글 추천
	public int recommend(String bno);
	// 글 추천 취소
	public int unrecommend(String bno);
	// 글 추천 수
	public int getRecommendCnt(int bno);
	// 글 신고
	public int report(int bno);
	// 글 신고 수
	public int getReportCnt(int bno);
	// 글 쓰기
	public void write(Board board);
	// 글 수정
	public int update(@Param("title") String title,@Param("content") String content, @Param("bno") String bno);
	// 글 삭제
	public void delete(@Param("bno") String bno,@Param("categoriName") String categoriName);
	// 자신의 글 확인
	public String postSearch(@Param("bno") String bno, @Param("writer") String writer);
	// 글 댓글 갯수
	public int upReplyCnt(int bno);
	// 글 검색
	public List<Board> boardSearch(@Param("startRow")int startRow, @Param("endRow") int endRow, @Param("categoriName") String categoriName,@Param("search") String search);
	// 누구의 글
	public String whoWriter(String bno);
}
