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

import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Venue;
import com.ketang.util.DateUtil;


@Service("venueService")
public class VenueServiceImpl implements VenueService {
	
	
	@Resource
	private VenueDao venueDao   ;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Venue repalce(Venue curr,Venue origin){
		
		if(curr.getTitle() ==null){
			curr.setTitle(origin.getTitle());
		}
		if(curr.getVideo() ==null){
			curr.setVideo(origin.getVideo());
		}
		if(curr.getVideoDesc() ==null){
			curr.setVideoDesc(origin.getVideoDesc());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		
		
		
		if(curr.getClickHit()==null){
			curr.setClickHit(origin.getClickHit());
		}
		if(curr.getReplyHit() ==null){
			curr.setReplyHit(origin.getReplyHit());
		}
		if(curr.getState() ==null){
			curr.setState(origin.getState());
		}
		if(curr.getContent() ==null){
			curr.setContent(origin.getContent());
		}
		if(curr.getImageUrl()==null){
			curr.setImageUrl(origin.getImageUrl());
		}
		
		
		if(curr.getVenueType()  ==null){
			curr.setVenueType(origin.getVenueType());
		}
		if(curr.getTop() ==null){
			curr.setTop(origin.getTop());
		}
		if(curr.getSort()  ==null){
			curr.setSort(origin.getSort());
		}
		if(curr.getSearchHit() ==null){
			curr.setSearchHit(origin.getSearchHit());
		}
		if(curr.getHot()  ==null){
			curr.setHot(origin.getHot());
		}
		
		
		if(curr.getPrice()  ==null){
			curr.setPrice(origin.getPrice());
		}
		if(curr.getFileUrl()   ==null){
			curr.setFileUrl(origin.getFileUrl());
		}
		
		return curr;
	}
	
	@Override
	public void update(Venue venue) {
		Venue origin = venueDao.findId(venue.getId());
		venue = repalce(venue, origin);
		venueDao.save(venue);
	}
	
	@Override
	public List<Venue> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "sort");
		Page<Venue> pages = venueDao.findAll(new Specification<Venue>() {
			
			@Override
			public Predicate toPredicate(Root<Venue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 加入 等于 
				if (map.get("top") != null) {
					predicate.getExpressions().add(cb.equal(root.get("top"), map.get("top")));
				}
				// 加入 等于 
				if (map.get("hot") != null) {
					predicate.getExpressions().add(cb.equal(root.get("hot"), map.get("hot")));
				}
				// 加入 等于 
				if (map.get("state") != null) {
					predicate.getExpressions().add(cb.equal(root.get("state"), map.get("state")));
				}
				
				// 加入 等于 
				if (map.get("venueType") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venueType"), map.get("venueType")));
				}
				
				
				// 模糊查询
				if (map.get("q") != null) {
					predicate.getExpressions().add(cb.or(
							
							cb.like(root.get("title"),"%"+map.get("q").toString()+"%"), 
							cb.like(root.get("content"),"%"+map.get("q").toString()+"%"),
							cb.like(root.get("videoDesc"),"%"+map.get("q").toString()+"%")
							
							
							));
				}
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=venueDao.count(new Specification<Venue>() {
			@Override
			public Predicate toPredicate(Root<Venue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				 
				// 加入 等于 
				if (map.get("top") != null) {
					predicate.getExpressions().add(cb.equal(root.get("top"), map.get("top")));
				}
				// 加入 等于 
				if (map.get("state") != null) {
					predicate.getExpressions().add(cb.equal(root.get("state"), map.get("state")));
				}
				// 加入 等于 
				if (map.get("hot") != null) {
					predicate.getExpressions().add(cb.equal(root.get("hot"), map.get("hot")));
				}
				// 加入 等于 
				if (map.get("venueType") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venueType"), map.get("venueType")));
				}
				
				// 模糊查询
				if (map.get("q") != null) {
					predicate.getExpressions().add(cb.or(
							
							cb.like(root.get("title"),"%"+map.get("q").toString()+"%"), 
							cb.like(root.get("content"),"%"+map.get("q").toString()+"%"),
							cb.like(root.get("videoDesc"),"%"+map.get("q").toString()+"%")
							
							
							));
				}
				
				
				return predicate;
			}
		});
		return count;
	}

	
	
	
	
	
}
