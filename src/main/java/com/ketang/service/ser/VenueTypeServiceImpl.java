package com.ketang.service.ser;


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

import com.ketang.dao.ser.VenueTypeDao;
import com.ketang.entity.ser.VenueType;


@Service("venueTypeService")
public class VenueTypeServiceImpl implements VenueTypeService {
	
	@Resource
	private VenueTypeDao venueTypeDao   ;
	
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public VenueType repalce(VenueType curr,VenueType origin){
		if(curr.getName()==null){
			curr.setName(origin.getName());
		}
		if(curr.getUseIt()==null){
			curr.setUseIt(origin.getUseIt());
		}
		if(curr.getSort() ==null){
			curr.setSort(origin.getSort());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		
		if(curr.getUpdateDateTime()==null){
			curr.setUpdateDateTime(origin.getUpdateDateTime());
		}
		if(curr.getImageUrl() ==null){
			curr.setImageUrl(origin.getImageUrl());
		}
		return curr;
	}
	
	@Override
	public void update(VenueType venueType) {
		VenueType origin = venueTypeDao.findId(venueType.getId());
		venueType = repalce(venueType, origin);
		venueTypeDao.save(venueType);
	}
	
	
	@Override
	public List<VenueType> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "sort");
		Page<VenueType> pages = venueTypeDao.findAll(new Specification<VenueType>() {
			
			@Override
			public Predicate toPredicate(Root<VenueType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("useIt") != null) {
					predicate.getExpressions().add(cb.equal(root.get("useIt"), map.get("useIt")));
				}
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=venueTypeDao.count(new Specification<VenueType>() {
			@Override
			public Predicate toPredicate(Root<VenueType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				 
				// 加入 等于 
				if (map.get("useIt") != null) {
					predicate.getExpressions().add(cb.equal(root.get("useIt"), map.get("useIt")));
				}
				
				return predicate;
			}
		});
		return count;
	}
	
	
	
}
