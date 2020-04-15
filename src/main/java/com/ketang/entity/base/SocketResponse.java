package com.ketang.entity.base;


//服务器向浏览器发送的数据 实体
public class SocketResponse {
	
	
	private String responseMessage;
	
	
	
	public SocketResponse(String responseMessage) {
		super();
		this.responseMessage = responseMessage;
	}
	

	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
	
	
}
