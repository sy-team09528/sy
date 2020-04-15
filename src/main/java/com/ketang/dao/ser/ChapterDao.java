package com.ketang.dao.ser;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.Chapter;


public interface ChapterDao extends JpaRepository<Chapter,Integer>,JpaSpecificationExecutor<Chapter> {
	
	@Query(value="select * from t_chapter  where id = ?1",nativeQuery = true)
	public Chapter findId(Integer id);
	
}



