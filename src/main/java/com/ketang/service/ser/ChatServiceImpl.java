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

import com.ketang.dao.ser.ChatDao;
import com.ketang.dao.ser.ReplyDao;
import com.ketang.entity.ser.Chat;
import com.ketang.entity.ser.Reply;


@Service("chatService")
public class ChatServiceImpl implements ChatService {
	
	@Resource
	private ChatDao chatDao ;
	
	@Override
	public List<Chat> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Chat> pages = chatDao.findAll(new Specification<Chat>() {
			
			@Override
			public Predicate toPredicate(Root<Chat> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		Long count=chatDao.count(new Specification<Chat>() {
			@Override
			public Predicate toPredicate(Root<Chat> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
