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
import com.ketang.dao.ser.NoteDao;
import com.ketang.entity.ser.Note;
import com.ketang.entity.ser.Order;



@Service("noteService")
public class NoteServiceImpl implements NoteService {

	@Resource
	private NoteDao noteDao ;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Note repalce(Note curr,Note origin){
		
		if(curr.getTitle()  ==null){
			curr.setTitle(origin.getTitle());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getContent()   ==null){
			curr.setContent(origin.getContent());
		}
		if(curr.getMember()   ==null){
			curr.setMember(origin.getMember());
		}
		
		return curr;
	}
	
	
	@Override
	public void update(Note note) {
		Note origin = noteDao.findId(note.getId());
		note = repalce(note, origin);
		noteDao.save(note);
	}

	
	@Override
	public List<Note> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Note> pages = noteDao.findAll(new Specification<Note>() {
			
			@Override
			public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		Long count=noteDao.count(new Specification<Note>() {
			@Override
			public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
