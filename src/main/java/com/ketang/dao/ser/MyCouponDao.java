package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ketang.entity.ser.MyCoupon;

public interface MyCouponDao extends JpaRepository<MyCoupon,Integer>,JpaSpecificationExecutor<MyCoupon> {
	
	@Query(value="select * from t_my_coupon  where id = ?1",nativeQuery = true)
	public MyCoupon findId(Integer id);
	
	
	
}
