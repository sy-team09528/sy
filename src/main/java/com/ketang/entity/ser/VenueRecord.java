package com.ketang.entity.ser;

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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ketang.entity.base.CustomDateSerializer;
import com.ketang.entity.base.CustomDateTimeSerializer;
/**
 * @author Administrator
 */
@Entity
@Table(name="t_venue_record")
public class VenueRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="venueId")
	private Venue venue ; // 
	
	@Temporal(TemporalType.DATE) 
	private Date createDate;//添加时间

	@Column(length=10)
	private Integer clickHit;//查看次数 
	
	@Column(length=10)
	private Integer searchHit; //搜索次数 
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getClickHit() {
		return clickHit;
	}

	public void setClickHit(Integer clickHit) {
		this.clickHit = clickHit;
	}

	public Integer getSearchHit() {
		return searchHit;
	}

	public void setSearchHit(Integer searchHit) {
		this.searchHit = searchHit;
	}
	
	
	
	
	
	
}
