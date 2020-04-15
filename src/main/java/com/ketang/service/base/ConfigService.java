package com.ketang.service.base;

import com.ketang.entity.base.Config;

public interface ConfigService {
	
	public void update(Config config);
	
	/**
	 * init 方法用这个
	 */
	public Config findById(Integer id);
	
	
}
