package com.ketang.dao.ser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.Venue;

public interface ReplyDao extends JpaRepository<Reply,Integer>,JpaSpecificationExecutor<Reply> {
	
	@Query(value="select * from t_reply  where id = ?1",nativeQuery = true)
	public Reply findId(Integer id);
	
	
	public List<Reply> findByVenue(Venue venue);
	
}
