package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.PostItem;

public interface PostItemDao extends JpaRepository<PostItem,Integer>,JpaSpecificationExecutor<PostItem> {
	
	@Query(value="select * from t_post_item  where id = ?1",nativeQuery = true)
	public PostItem findId(Integer id);
	
}
