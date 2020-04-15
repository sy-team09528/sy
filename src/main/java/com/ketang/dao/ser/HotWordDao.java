package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.HotWord;

public interface HotWordDao extends JpaRepository<HotWord,Integer>,JpaSpecificationExecutor<HotWord> {
	
	
	@Query(value="select * from t_hot_word  where id = ?1",nativeQuery = true)
	public HotWord findId(Integer id);

}
