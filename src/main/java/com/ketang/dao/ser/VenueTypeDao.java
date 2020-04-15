package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.VenueType;


public interface VenueTypeDao extends JpaRepository<VenueType,Integer>,JpaSpecificationExecutor<VenueType> {
	
	@Query(value="select * from t_a_venue_type  where id = ?1",nativeQuery = true)
	public VenueType findId(Integer id);
	
	
}
