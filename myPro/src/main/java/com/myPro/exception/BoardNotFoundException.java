package com.myPro.exception;

public class BoardNotFoundException extends RuntimeException {
	@Override
	public String getMessage() {
		return "글을 찾을 수 없습니다";
	}
}
