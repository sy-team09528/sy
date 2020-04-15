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

import com.ketang.dao.ser.CollectionDao;
import com.ketang.entity.ser.Chat;
import com.ketang.entity.ser.Collection;


@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {
	
	
	@Resource
	private CollectionDao collectionDao ;
	
	@Override
	public List<Collection> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Collection> pages = collectionDao.findAll(new Specification<Collection>() {
			
			@Override
			public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=collectionDao.count(new Specification<Collection>() {
			@Override
			public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				
				// 加入 等于 
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				return predicate;
			}
		});
		return count;
	}
	
	
	
}
