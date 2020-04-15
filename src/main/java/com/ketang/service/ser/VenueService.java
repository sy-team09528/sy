package com.ketang.service.ser;

import java.util.List;
import java.util.Map;

import com.ketang.entity.ser.Venue;


public interface VenueService {
	
	
	public void update(Venue venue);
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Venue> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
}
