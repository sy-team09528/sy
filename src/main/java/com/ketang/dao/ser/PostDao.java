package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.Post;

public interface PostDao extends JpaRepository<Post,Integer>,JpaSpecificationExecutor<Post> {
	
	@Query(value="select * from t_post where id = ?1",nativeQuery = true)
	public Post findId(Integer id);
	
}
