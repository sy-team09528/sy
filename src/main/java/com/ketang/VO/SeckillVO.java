package com.ketang.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ketang.entity.ser.VenueType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


public class SeckillVO {
	
	private Integer id;

	private Integer venue_id;
	
	private Integer num;
	
	private Integer state;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss",
			timezone = "GMT+8"
	)
	private Date create_date_time;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss",
			timezone = "GMT+8"
	)
	private Date start_date_time;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss",
			timezone = "GMT+8"
	)
	private Date end_date_time;
	
	private VenueType venueType; //

	private  String imageUrl;//封面
	
	private  BigDecimal price;//价 格
	
	private String title;//名称

	private String countDown;//倒计时


	public SeckillVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SeckillVO(Integer id, Integer venue_id, Integer num, Integer state, Date create_date_time, Date start_date_time, Date end_date_time, VenueType venueType, String imageUrl, BigDecimal price, String title, String countDown) {
		this.id = id;
		this.venue_id = venue_id;
		this.num = num;
		this.state = state;
		this.create_date_time = create_date_time;
		this.start_date_time = start_date_time;
		this.end_date_time = end_date_time;
		this.venueType = venueType;
		this.imageUrl = imageUrl;
		this.price = price;
		this.title = title;
		this.countDown = countDown;
	}

	@Override
	public String toString() {
		return "SeckillVO{" +
				"id=" + id +
				", venue_id=" + venue_id +
				", num=" + num +
				", state=" + state +
				", create_date_time=" + create_date_time +
				", start_date_time=" + start_date_time +
				", end_date_time=" + end_date_time +
				", venueType=" + venueType +
				", imageUrl='" + imageUrl + '\'' +
				", price=" + price +
				", title='" + title + '\'' +
				", countDown='" + countDown + '\'' +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVenue_id() {
		return venue_id;
	}

	public void setVenue_id(Integer venue_id) {
		this.venue_id = venue_id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreate_date_time() {
		return create_date_time;
	}

	public void setCreate_date_time(Date create_date_time) {
		this.create_date_time = create_date_time;
	}

	public Date getStart_date_time() {
		return start_date_time;
	}

	public void setStart_date_time(Date start_date_time) {
		this.start_date_time = start_date_time;
	}

	public Date getEnd_date_time() {
		return end_date_time;
	}

	public void setEnd_date_time(Date end_date_time) {
		this.end_date_time = end_date_time;
	}

	public VenueType getVenueType() {
		return venueType;
	}

	public void setVenueType(VenueType venueType) {
		this.venueType = venueType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
}
