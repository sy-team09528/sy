package com.ketang.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.base.User;

public interface UserDao   extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {
	
	@Query(value="select * from t_a_user where id = ?1",nativeQuery = true)
	public User findId(Integer id);
	
	public User findByName(String name);
	
	
}
