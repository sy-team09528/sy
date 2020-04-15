package com.ketang.service.ser;

import java.util.List;
import java.util.Map;
import com.ketang.entity.ser.MyCoupon;

public interface MyCouponService {
	
	public void update(MyCoupon myCoupon );
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<MyCoupon> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
}
