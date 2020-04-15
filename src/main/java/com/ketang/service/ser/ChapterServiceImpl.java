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

import com.ketang.dao.ser.ChapterDao;
import com.ketang.dao.ser.ChatDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Link;


@Service("chapterService")
public class ChapterServiceImpl implements ChapterService {
	
	@Resource
	private ChapterDao chapterDao ;
	
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Chapter repalce(Chapter curr,Chapter origin){
		if(curr.getTitle() ==null){
			curr.setTitle(origin.getTitle());
		}
		if(curr.getSort() ==null){
			curr.setSort(origin.getSort());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getVenue() ==null){
			curr.setVenue(origin.getVenue());
		}
		
		
		if(curr.getContent()  ==null){
			curr.setContent(origin.getContent());
		}
		
		return curr;
	}
	
	
	@Override
	public void update(Chapter chapter) {
		Chapter origin = chapterDao.findId(chapter.getId());
		chapter = repalce(chapter, origin);
		chapterDao.save(chapter);		
	}

	@Override
	public List<Chapter> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "sort");
		Page<Chapter> pages = chapterDao.findAll(new Specification<Chapter>() {
			
			@Override
			public Predicate toPredicate(Root<Chapter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("venue") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venue"), map.get("venue")));
				}
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=chapterDao.count(new Specification<Chapter>() {
			@Override
			public Predicate toPredicate(Root<Chapter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				 
				// 加入 等于 
				if (map.get("venue") != null) {
					predicate.getExpressions().add(cb.equal(root.get("venue"), map.get("venue")));
				}
				
				return predicate;
			}
		});
		return count;
	}
	
	
	
}
