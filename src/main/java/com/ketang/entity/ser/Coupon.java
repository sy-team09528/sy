package com.ketang.entity.ser;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * #优惠券
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_coupon")
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "名称 不能为空！")
	@Column(length = 50)
	private String name; // 名称
	@NotNull(message = "排序号不能为空！")
	@Column(length = 10)
	private Integer sort;
	@NotNull(message = "金额不能为空！")
	@Column(precision = 10, scale = 2)
	private BigDecimal jine;// 金额
	@NotNull(message = "数量不能为空！")
	@Column(length = 10)
	private Integer num;
	
	
	
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public BigDecimal getJine() {
		return jine;
	}

	public void setJine(BigDecimal jine) {
		this.jine = jine;
	}

	
	
	
}
