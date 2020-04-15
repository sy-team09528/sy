package com.ketang.service.ser;

import java.util.List;
import java.util.Map;

import com.ketang.entity.ser.VenueType;

public interface VenueTypeService {

	public void update(VenueType venueType  );
	
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<VenueType> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
	
}
