package com.ketang.entity.ser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_seckill")
public class Seckill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer venue_id;
	
	private Integer num;
	
	private Integer state;
    @Temporal(TemporalType.TIMESTAMP)
	private Date create_date_time;
    @Temporal(TemporalType.TIMESTAMP)
	private Date start_date_time;
    @Temporal(TemporalType.TIMESTAMP)
	private Date end_date_time;
	
	public Seckill() {
		super();
		// TODO Auto-generated constructor stub
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
	public void setEnd_date_time(Date  end_date_time) {
		this.end_date_time = end_date_time;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Seckill(Integer id, Integer venue_id, Integer num, Integer state, Date create_date_time,
                   Date start_date_time, Date end_date_time) {
		super();
		this.id = id;
		this.venue_id = venue_id;
		this.num = num;
		this.state = state;
		this.create_date_time = create_date_time;
		this.start_date_time = start_date_time;
		this.end_date_time = end_date_time;
	}
	@Override
	public String toString() {
		return "Seckill [id=" + id + ", venue_id=" + venue_id + ", num=" + num + ", state=" + state
				+ ", create_date_time=" + create_date_time + ", start_date_time=" + start_date_time + ", end_date_time="
				+ end_date_time + "]";
	}
	
}
	
	
	

