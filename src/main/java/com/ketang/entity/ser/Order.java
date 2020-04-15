package com.ketang.entity.ser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ketang.entity.base.CustomDateTimeSerializer;

import net.sf.json.JSONObject;

/**
 * #订单实体
 * @author Administrator
 */
@Entity
@Table(name = "t_order")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length=100)
	private  String num;//订单号 可以使用时间 搓
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;//创建时间
	@Column(precision = 10, scale = 2)
	private BigDecimal amount;//订单金额
	@ManyToOne
	@JoinColumn(name="memberId")
	private Member member  ; // 对应的会员
	
	
	@ManyToOne
	@JoinColumn(name="venueId")
	private Venue venue    ; // 对应的 
	@Column(length=10)
	private Integer state;//0未付款  1已付款
	@ManyToOne
	@JoinColumn(name="myCouponId")
	private MyCoupon myCoupon      ; // 对应的 
	
	
	
	public MyCoupon getMyCoupon() {
		return myCoupon;
	}
	public void setMyCoupon(MyCoupon myCoupon) {
		this.myCoupon = myCoupon;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
	
	
	
}
