package com.ketang.service.ser;

import java.util.List;
import java.util.Map;

import com.ketang.entity.ser.Member;

public interface MemberService {
	
	public void update(Member member   );
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Member> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
	
	
}
