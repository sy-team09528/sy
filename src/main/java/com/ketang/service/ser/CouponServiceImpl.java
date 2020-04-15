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
import com.ketang.dao.ser.CouponDao;
import com.ketang.entity.ser.Collection;
import com.ketang.entity.ser.Coupon;
import com.ketang.entity.ser.LunBo;
import com.ketang.util.FileUtil;
import com.ketang.util.StringUtil;


@Service("couponService")
public class CouponServiceImpl implements CouponService {
	
	@Resource
	private CouponDao couponDao   ;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Coupon repalce(Coupon curr,Coupon origin){
		if(curr.getName()==null){
			curr.setName(origin.getName());
		}
		if(curr.getNum() ==null){
			curr.setNum(origin.getNum());
		}
		if(curr.getJine() ==null){
			curr.setJine(origin.getJine());
		}
		if(curr.getSort() ==null){
			curr.setSort(origin.getSort());
		}
		return curr;
	}
	
	@Override
	public void update(Coupon coupon) {
		Coupon origin = couponDao.findId(coupon.getId());
		coupon = repalce(coupon, origin);
		couponDao.save(coupon);
	}
	
	@Override
	public List<Coupon> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "sort");
		Page<Coupon> pages = couponDao.findAll(new Specification<Coupon>() {
			
			@Override
			public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		Long count=couponDao.count(new Specification<Coupon>() {
			@Override
			public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
