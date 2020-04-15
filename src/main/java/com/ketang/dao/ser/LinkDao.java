package com.ketang.dao.ser;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.Link;



public interface LinkDao   extends JpaRepository<Link,Integer>,JpaSpecificationExecutor<Link> {
	
	
	@Query(value="select * from t_link  where id = ?1",nativeQuery = true)
	public Link findId(Integer id);
	
	
}
