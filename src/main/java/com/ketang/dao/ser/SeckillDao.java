package com.ketang.dao.ser;

import com.ketang.entity.ser.Seckill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SeckillDao extends JpaRepository<Seckill,Integer>,JpaSpecificationExecutor<Seckill>{

	@Query(value="select * from t_seckill   where id = ?1",nativeQuery = true)
	public Seckill findId(Integer id);
}
