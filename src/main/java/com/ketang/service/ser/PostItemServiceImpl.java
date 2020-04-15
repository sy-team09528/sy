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

import com.ketang.dao.ser.OrderDao;
import com.ketang.dao.ser.PostItemDao;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.PostItem;

@Service("postItemService")
public class PostItemServiceImpl implements PostItemService {
		
	@Resource
	private PostItemDao postItemDao ;

	@Override
	public List<PostItem> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<PostItem> pages = postItemDao.findAll(new Specification<PostItem>() {
			
			@Override
			public Predicate toPredicate(Root<PostItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (map.get("postId") != null) {
					predicate.getExpressions().add(cb.equal(root.get("postId"), map.get("postId")));
				}
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	
	
	
}
