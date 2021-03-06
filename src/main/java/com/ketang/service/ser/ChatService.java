package com.ketang.service.ser;

import java.util.List;
import java.util.Map;

import com.ketang.entity.ser.Chat;
import com.ketang.entity.ser.Reply;


public interface ChatService {
	
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Chat> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
	
}
