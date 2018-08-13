package com.icia.aboard2.dao;


import org.apache.ibatis.annotations.*;

import com.icia.aboard2.entity.*;

public interface AttachRepository {
	
	void insert(Attachment attachment);

	Attachment list(Integer bno);

	void deleteAll(int bno);

	Attachment getAttachment(@Param("bno") int bno, @Param("ano") int ano);

}
