package com.jsp.bankApp.helper;

import java.util.List;

public class ResponseStructure<T> {
	
	private int statusCode;
	private T data;
	private String Message;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	public void setData(List<T> datas) {
		for(T d:datas) {
			setData(d);	
		}
	}

}
