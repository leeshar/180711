package com.myPro.dao;


import org.apache.ibatis.annotations.*;

import com.myPro.entity.*;

public interface AttachRepository {
	// 이미지 추가
	void aInsert(Attachment attachment);
	// 이미지 리스트
	Attachment aList(Integer bno);
	// 이미지가져오기
	Attachment aGetAttachment(@Param("bno") int bno, @Param("ano") int ano);

}
