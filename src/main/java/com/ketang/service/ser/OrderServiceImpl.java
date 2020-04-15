package com.ketang.service.ser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.OrderDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Order;
import com.ketang.util.DateUtil;



@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
	@Resource
	private OrderDao orderDao;
	@Resource
	private MemberDao memberDao  ;
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Order repalce(Order curr,Order origin){
		
		if(curr.getNum() ==null){
			curr.setNum(origin.getNum());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getAmount()  ==null){
			curr.setAmount(origin.getAmount());
		}
		if(curr.getMember()   ==null){
			curr.setMember(origin.getMember());
		}
		
		
		if(curr.getState()   ==null){
			curr.setState(origin.getState());
		}
		if(curr.getVenue()   ==null){
			curr.setVenue(origin.getVenue());
		}
		
		return curr;
	}
	
	
	@Override	
	public void update(Order order) {
		Order origin = orderDao.findId(order.getId());
		order = repalce(order, origin);
		orderDao.save(order);
	}
	
	@Override
	public List<Order> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Order> pages = orderDao.findAll(new Specification<Order>() {
			
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				if (map.get("state") != null) {
					predicate.getExpressions().add(cb.equal(root.get("state"), map.get("state")));
				}
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=orderDao.count(new Specification<Order>() {
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				
				if (map.get("member") != null) {
					predicate.getExpressions().add(cb.equal(root.get("member"), map.get("member")));
				}
				
				if (map.get("state") != null) {
					predicate.getExpressions().add(cb.equal(root.get("state"), map.get("state")));
				}
				return predicate;
			}
		});
		return count;
	}
	
	
	
}
