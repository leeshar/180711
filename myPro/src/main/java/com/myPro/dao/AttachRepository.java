package com.myPro.dao;


import org.apache.ibatis.annotations.*;

import com.myPro.entity.*;

public interface AttachRepository {
	
	void insert(Attachment attachment);

	Attachment list(Integer bno);

	Attachment getAttachment(@Param("bno") int bno, @Param("ano") int ano);

}
