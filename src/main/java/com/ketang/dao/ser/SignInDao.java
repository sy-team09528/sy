package com.ketang.dao.ser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.SignIn;


public interface SignInDao  extends JpaRepository<SignIn,Integer>,JpaSpecificationExecutor<SignIn> {

	@Query(value="select * from t_sign_in  where  create_date = ?1 and member_id=?2 ",nativeQuery = true)
	public List<SignIn> findByDateAndMember(String date,Integer memberId);
	
}
