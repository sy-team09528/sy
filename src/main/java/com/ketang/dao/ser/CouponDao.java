package com.ketang.dao.ser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ketang.entity.ser.Coupon;

public interface CouponDao  extends JpaRepository<Coupon,Integer>,JpaSpecificationExecutor<Coupon> {
	
	@Query(value="select * from t_coupon  where id = ?1",nativeQuery = true)
	public Coupon findId(Integer id);

}
