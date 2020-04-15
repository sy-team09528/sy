package com.ketang.service.ser;

import java.util.List;
import java.util.Map;

import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.VenueRecord;

public interface VenueRecordService {
	
	
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<VenueRecord> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	
	
}
