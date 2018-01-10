package com.maxplus1.demo.common;

public enum HttpStatusCode {
	OK(200), NOT_FOUND(404),ERROR(300),TIMEOUT(301);
	
	private int code;

	private HttpStatusCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
