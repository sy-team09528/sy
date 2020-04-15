package com.ketang.service.ser;

import java.util.List;
import java.util.Map;
import com.ketang.entity.ser.PostItem;

public interface PostItemService {
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<PostItem> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	
	
}
