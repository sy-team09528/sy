package com.ketang.dao.ser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.Member;

public interface MemberDao extends JpaRepository<Member,Integer>,JpaSpecificationExecutor<Member> {
	
	@Query(value="select * from t_a_member  where id = ?1",nativeQuery = true)
	public Member findId(Integer id);
	
	
	public List<Member> findByName(String name);
}
