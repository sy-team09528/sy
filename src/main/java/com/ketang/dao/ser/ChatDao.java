package com.ketang.dao.ser;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.Chat;


public interface ChatDao extends JpaRepository<Chat,Integer>,JpaSpecificationExecutor<Chat> {
	
	@Query(value="select * from t_chat  where id = ?1",nativeQuery = true)
	public Chat findId(Integer id);
	
	
	@Query(value="select * from t_chat  where venue_id = ?1",nativeQuery = true)
	public List<Chat> findByVenueId(Integer venueId );
	
	
}
