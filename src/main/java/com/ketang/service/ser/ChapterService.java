package com.ketang.service.ser;

import java.util.List;
import java.util.Map;
import com.ketang.entity.ser.Chapter;


public interface ChapterService {
	
	public void update(Chapter chapter );
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Chapter> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
}
