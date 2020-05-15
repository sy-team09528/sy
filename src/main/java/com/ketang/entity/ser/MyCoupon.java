package com.ketang.entity.ser;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ketang.entity.base.CustomDateTimeSerializer;


@Entity
@Table(name = "t_my_coupon")
public class MyCoupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "名称 不能为空！")
	@Column(length = 50)
	private String name; // 名称

	@NotNull(message = "金额不能为空！")
	@Column(precision = 10, scale = 2)
	private BigDecimal jine;// 金额

	@Column(length = 5)
	private Integer state;// 0未使用  1已使用

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;//领取时间
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date useDateTime;//使用时间
	@OneToOne
	@JoinColumn(name="memberId")
	private Member member  ; // 
	
	
	
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
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
	public BigDecimal getJine() {
		return jine;
	}
	public void setJine(BigDecimal jine) {
		this.jine = jine;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	//	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDateTime() {
			return createDateTime;
		}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	//@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUseDateTime() {
		return useDateTime;
	}
	public void setUseDateTime(Date useDateTime) {
		this.useDateTime = useDateTime;
	}
	
	
	
	
	
}
