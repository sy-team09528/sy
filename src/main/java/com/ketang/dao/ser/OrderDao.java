package com.ketang.dao.ser;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.Order;


public interface OrderDao extends JpaRepository<Order,Integer>,JpaSpecificationExecutor<Order> {
	
	
	@Query(value="select * from t_order where id = ?1",nativeQuery = true)
	public Order findId(Integer id);
	
	@Query(value="select COUNT(*)  from t_order where member_id = ?1 and venue_id = ?2",nativeQuery = true)
	public Integer find_memberId_venueId(Integer memberId,Integer venudId);
	
}
