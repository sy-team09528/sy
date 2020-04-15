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
import com.ketang.dao.ser.PostDao;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.Post;

@Service("postService")
public class PostServiceImpl implements PostService {
	
	@Resource
	private PostDao postDao  ;
	
	@Override
	public List<Post> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Post> pages = postDao.findAll(new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				
				// 模糊查询
				if (map.get("q") != null) {
					predicate.getExpressions().add(cb.or(
							cb.like(root.get("title"),"%"+map.get("q").toString()+"%"), 
							cb.like(root.get("content"),"%"+map.get("q").toString()+"%")
							));
				}
				
				
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=postDao.count(new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				// 模糊查询
				if (map.get("q") != null) {
					predicate.getExpressions().add(cb.or(
							cb.like(root.get("title"),"%"+map.get("q").toString()+"%"), 
							cb.like(root.get("content"),"%"+map.get("q").toString()+"%")
							));
				}
				
				return predicate;
			}
		});
		return count;
	}
	
	
	
}
