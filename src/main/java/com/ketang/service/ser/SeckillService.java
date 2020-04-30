package com.ketang.service.ser;

import com.ketang.VO.SeckillVO;
import com.ketang.entity.ser.Seckill;

import java.util.List;
import java.util.Map;


public interface SeckillService {

	public List<SeckillVO> list(Map<String, Object> map, Integer page, Integer pageSize);

	public Long getTotal(Map<String, Object> map);

	public void update(Seckill seckill);
}
