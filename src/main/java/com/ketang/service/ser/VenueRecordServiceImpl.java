package com.ketang.service.ser;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ketang.dao.ser.VenueRecordDao;
import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.VenueRecord;
import com.ketang.util.DateUtil;


@Service("venueRecordService")
public class VenueRecordServiceImpl implements VenueRecordService {
	
	@Resource
	private VenueRecordDao venueRecordDao ;
	
	@Override
	public List<VenueRecord> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "id");
		Page<VenueRecord> pages = venueRecordDao.findAll(new Specification<VenueRecord>() {
			
			@Override
			public Predicate toPredicate(Root<VenueRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("venue") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venue"), map.get("venue")));
				}
				
				//日期  范围 
				if(map.get("date1") != null && map.get("date2") != null) {
					Date start = DateUtil.formatString(map.get("date1").toString(), "yyyy-MM-dd");
					Date end = DateUtil.formatString(map.get("date2").toString(), "yyyy-MM-dd");
					predicate.getExpressions().add(cb.between(root.get("createDate"),start,end));
				}
				
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	
	
	
}
