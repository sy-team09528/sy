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
import com.ketang.dao.ser.MyCouponDao;
import com.ketang.entity.ser.MyCoupon;
import com.ketang.entity.ser.Order;


@Service("myCouponService")
public class MyCouponServiceImpl implements MyCouponService {
	
	@Resource
	private MyCouponDao myCouponDao  ;
	
	
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public MyCoupon repalce(MyCoupon curr,MyCoupon origin){
		
		if(curr.getUseDateTime()  ==null){
			curr.setUseDateTime(origin.getUseDateTime());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getJine()   ==null){
			curr.setJine(origin.getJine());
		}
		if(curr.getMember()   ==null){
			curr.setMember(origin.getMember());
		}
		
		
		if(curr.getState()   ==null){
			curr.setState(origin.getState());
		}
		if(curr.getName()    ==null){
			curr.setName(origin.getName());
		}
		
		return curr;
	}
	
	@Override
	public void update(MyCoupon myCoupon) {
		MyCoupon origin = myCouponDao.findId(myCoupon.getId());
		myCoupon = repalce(myCoupon, origin);
		myCouponDao.save(myCoupon);
	}

	@Override
	public List<MyCoupon> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<MyCoupon> pages = myCouponDao.findAll(new Specification<MyCoupon>() {
			
			@Override
			public Predicate toPredicate(Root<MyCoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		Long count=myCouponDao.count(new Specification<MyCoupon>() {
			@Override
			public Predicate toPredicate(Root<MyCoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
