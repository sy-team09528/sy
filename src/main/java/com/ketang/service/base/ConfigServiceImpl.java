package com.ketang.service.base;



import javax.annotation.Resource;
import javax.persistence.Column;
import javax.servlet.ServletContext;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ketang.dao.base.ConfigDao;
import com.ketang.entity.base.Config;
import com.ketang.entity.base.Menu;
import com.ketang.entity.base.Role;



@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private ConfigDao configDao;
	
	
	@Override
	public void update(Config config) {
		configDao.save(config);
	}
	
	
	/**
	 * init 方法用这个
	 */
	@Override
	public Config findById(Integer id) {
		return configDao.findId(id);
	}

}
