package com.icia.exception;

public class BoardNotFoundException extends Exception{
	@Override
	public String getMessage() {
		return "게시물을 찾을 수 없습니다.";
	}
}
