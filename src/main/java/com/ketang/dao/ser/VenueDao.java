package com.ketang.dao.ser;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;


public interface VenueDao  extends JpaRepository< Venue,Integer>,JpaSpecificationExecutor< Venue> {
	
	@Query(value="select * from t_b_venue   where id = ?1",nativeQuery = true)
	public Venue findId(Integer id);
	
	public  Venue  findByContent(String content);
	
	public Venue  findByTitle(String title);
	
	public	List<Venue> findByVenueType(VenueType venueType);
	
	@Query(value="SELECT * FROM  `t_b_venue`  ORDER BY click_hit DESC  LIMIT 0,4",nativeQuery = true)
	public	List<Venue> tuijian();
	
}