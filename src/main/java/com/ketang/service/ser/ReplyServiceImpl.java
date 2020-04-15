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

import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.ReplyDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Reply;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
	

	@Resource
	private ReplyDao replyDao       ;
	
	@Override
	public List<Reply> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Reply> pages = replyDao.findAll(new Specification<Reply>() {
			
			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("venue") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venue"), map.get("venue")));
				}
				
				// 加入 等于 
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				// 模糊查询
				if (map.get("q") != null) {
					predicate.getExpressions().add(cb.or(
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
		Long count=replyDao.count(new Specification<Reply>() {
			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				// 加入 等于 
				if (map.get("venue") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venue"), map.get("venue")));
				}
				
				// 加入 等于 
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				// 模糊查询
				if (map.get("q") != null) {
					predicate.getExpressions().add(cb.or(
							cb.like(root.get("content"),"%"+map.get("q").toString()+"%")
							));
				}
				
				
				
				return predicate;
			}
		});
		return count;
	}
	
	
	
	
}
