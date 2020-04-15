package com.ketang.dao.ser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.Collection;
import com.ketang.entity.ser.Member;

public interface CollectionDao  extends JpaRepository<Collection,Integer>,JpaSpecificationExecutor<Collection> {
	
	@Query(value="select * from t_collection  where id = ?1",nativeQuery = true)
	public Collection findId(Integer id);
	
	/**
	 * #查询此用户是否收藏这个场馆。 
	 * #根据 场馆id和用户id
	 * @param memberId
	 * @param venueId
	 */
	@Query(value="select * from t_collection  where member_id = ?1 and venue_id=?2",nativeQuery = true)
	public Collection findByMember_Venue(Integer memberId,Integer venueId);
	
	public List<Collection> findByMember(Member member);
	
	@Query(value="SELECT COUNT(*)  FROM `t_collection` WHERE venue_id=?1",nativeQuery = true)
	public Integer getTotalByVenue(Integer venue_id);
	
	
	@Query(value="SELECT COUNT(*)  FROM `t_collection` WHERE venue_id=?1 and  member_id = ?2 ",nativeQuery = true)
	public Integer getTotal_venueId_memberId(Integer venue_id,Integer member_id);
	
	
	
}
