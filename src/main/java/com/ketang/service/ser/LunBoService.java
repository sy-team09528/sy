package com.ketang.service.ser;

import java.util.List;
import java.util.Map;

import com.ketang.entity.ser.LunBo;

public interface LunBoService {
	
	
	public Integer update(LunBo lunbo   ,String webPath);
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<LunBo> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	public void delete(Integer id,String webPath);
	
	
	
}
