package com.ketang.service.ser;


import com.ketang.VO.SeckillVO;
import com.ketang.dao.ser.SeckillDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Seckill;
import com.ketang.entity.ser.Venue;
import com.ketang.util.RedisKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {
	@Resource
	private VenueDao venueDao;
	@Resource
	private VenueService venueService;
	@Autowired
    SeckillDao seckillDao;
	@Autowired
	private RedisTemplate redisTemplate;
	
	Jedis jedis = new Jedis("localhost");

	//获取秒杀信息
	@Override
	public List<SeckillVO> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		
		Page<Seckill> pages = seckillDao.findAll(new Specification<Seckill>() {
			
			@Override
			public Predicate toPredicate(Root<Seckill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				
				// 加入 等于 
				if (map.get("state") != null) {
					predicate.getExpressions().add(cb.equal(root.get("state"), map.get("state")));
				}
				
				// 加入 等于 
				if (map.get("q") != null || map.get("venueType")!=null) {
					if (map.get("state") != null) {
						map.remove("state");
					}
					
					
					javax.persistence.criteria.Path<Object> path = root.get("venue_id");
					CriteriaBuilder.In<Object> in = cb.in(path);
					List<Venue> venusList=venueService.list(map, page, pageSize);
					for (int i = 0; i < venusList.size(); i++) {
						in.value(venusList.get(i).getId());
					}
					predicate.getExpressions().add(cb.and(cb.and(in)));
		
				}
				return predicate;
			}
		}, pageable);
		List<Seckill> seckillList=pages.getContent();
		List<SeckillVO> seckillVOs=new ArrayList<SeckillVO>();
		for (int i = 0; i < seckillList.size(); i++) {
			//判断商品秒杀时间是否结束
			Seckill seckill=seckillList.get(i);
			System.out.println(seckill.getVenue_id());
			Venue venue=venueDao.findId(seckill.getVenue_id());
			System.out.println(venue);
			SeckillVO seckillVO=new SeckillVO();
			seckillVO.setId(seckill.getId());
			seckillVO.setCreate_date_time(seckill.getCreate_date_time());
			seckillVO.setEnd_date_time(seckill.getEnd_date_time());
			seckillVO.setStart_date_time(seckill.getStart_date_time());
			seckillVO.setNum(seckill.getNum());
			seckillVO.setState(seckill.getState());
			seckillVO.setTitle(venue.getTitle());
			seckillVO.setVenueType(venue.getVenueType());
			seckillVO.setImageUrl(venue.getImageUrl());
			seckillVO.setPrice(venue.getPrice());
			seckillVO.setVenue_id(seckill.getVenue_id());
			seckillVOs.add(seckillVO);
			System.out.println(seckillVO);
		}
		return seckillVOs;
	}
	//获取数量
	@Override
	public Long getTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Long count=seckillDao.count(new Specification<Seckill>() {
			@Override
			public Predicate toPredicate(Root<Seckill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				// 加入 等于 
				if (map.get("state") != null) {
					predicate.getExpressions().add(cb.equal(root.get("state"), map.get("state")));
				}
				return predicate;
			}
		});
		return count;
	}

	//更新
	@Override
	public void update(Seckill seckill) {
		Seckill origin = seckillDao.findId(seckill.getId());
		seckill = repalce(seckill, origin);
		seckillDao.save(seckill);
	}

	@Override
	public long nowTime() {
		Date now = new Date();
		return now.getTime();
	}


	//辅助更新
	public Seckill repalce(Seckill curr, Seckill origin){
		System.err.println(curr);
		if(curr.getVenue_id() ==null){

			curr.setVenue_id(origin.getVenue_id());
		}else {
			redisTemplate.boundHashOps(RedisKeyEnum.seckillList.getKey()).delete(origin.getVenue_id());
		}
		if(curr.getNum() ==null){

			curr.setNum(origin.getNum());
//			jedis.set(curr.getId().toString(), curr.getNum().toString());
		}
		if(curr.getStart_date_time() ==null){
			curr.setStart_date_time(origin.getStart_date_time());
		}
		if(curr.getEnd_date_time()==null){
			curr.setEnd_date_time(origin.getEnd_date_time());
		}
		if(curr.getState()==null){
			curr.setState(origin.getState());
		}
		curr.setCreate_date_time(origin.getCreate_date_time());

		redisTemplate.boundHashOps(RedisKeyEnum.seckillList.getKey()).put(curr.getVenue_id(),curr.getNum());

		return curr;
}
}
