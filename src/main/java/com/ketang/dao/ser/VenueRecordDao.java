package com.ketang.dao.ser;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.VenueRecord;


public interface VenueRecordDao extends JpaRepository< VenueRecord,Integer>,JpaSpecificationExecutor< VenueRecord> {
	
	@Query(value="select * from t_venue_record   where id = ?1",nativeQuery = true)
	public VenueRecord findId(Integer id);
	
	
	/**
	 * #根据 时间和 venueid 查询   VenueRecord
	 */
	@Query(value="SELECT * FROM  t_venue_record  WHERE venue_id =?1 AND create_date=   ?2  ",nativeQuery = true)
	public VenueRecord findDateVenue(Integer venudId,String date );
	
}
