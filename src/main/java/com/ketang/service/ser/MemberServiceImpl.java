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

import com.ketang.dao.ser.LunBoDao;
import com.ketang.dao.ser.MemberDao;
import com.ketang.entity.ser.LunBo;
import com.ketang.entity.ser.Member;
import com.ketang.util.FileUtil;
import com.ketang.util.StringUtil;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Resource
	private MemberDao memberDao     ;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Member repalce(Member curr,Member origin){
		if(curr.getName()==null){
			curr.setName(origin.getName());
		}
		if(curr.getPwd() ==null){
			curr.setPwd(origin.getPwd());
		}
		if(curr.getNickName() ==null){
			curr.setNickName(origin.getNickName());
		}
		if(curr.getPhone() ==null){
			curr.setPhone(origin.getPhone());
		}
		
		
		if(curr.getSex() ==null){
			curr.setSex(origin.getSex());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getImageUrl()  ==null){
			curr.setImageUrl(origin.getImageUrl());
		}
		if(curr.getIntroduction() ==null){
			curr.setIntroduction(origin.getIntroduction());
		}
		if(curr.getBalance()  ==null){
			curr.setBalance(origin.getBalance());
		}
		return curr;
	}
	
	@Override
	public void update(Member member) {
		Member origin = memberDao.findId(member.getId());
		 member = repalce(member, origin);
		 memberDao.save(member);
	}
	

	@Override
	public List<Member> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Member> pages = memberDao.findAll(new Specification<Member>() {
			
			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("sex") != null) {
					predicate.getExpressions().add(cb.equal(root.get("sex"), map.get("sex")));
				}
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=memberDao.count(new Specification<Member>() {
			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				// 加入 等于 
				if (map.get("sex") != null) {
					predicate.getExpressions().add(cb.equal(root.get("sex"), map.get("sex")));
				}
				
				return predicate;
			}
		});
		return count;
	}
	
	
}
