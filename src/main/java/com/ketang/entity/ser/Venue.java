package com.ketang.entity.ser;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ketang.entity.base.CustomDateSerializer;
import com.ketang.entity.base.CustomDateTimeSerializer;

@Entity
@Table(name="t_b_venue")
public class Venue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message="标题不能为空！")
	@Column(length=200)
	private String title; //标题
	@NotEmpty(message="视频不能为空！")
	@Column(length=500)
	private String video; //视频
	@Column(length=200)
	private String videoDesc; //视频介绍
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//发布时间
	
	
	
	@Column(length=10)
	private Integer clickHit;//查看次数    播放次数
	@Column(length=10)
	private Integer replyHit; //回复次数  留言次数 
	@Column(length=10)
	private Integer state;//0未审校   1已审核
	@Lob
    @Basic(fetch=FetchType.LAZY)
	private String content; //html 代码  long text
	@Column(length=150)
	private  String imageUrl;//封面
	
	
	
	@ManyToOne
	@JoinColumn(name="venueTypeId")
	private VenueType venueType; //类型
	@Column(length=10)
	private Integer top;//首页 显示   0  1 
	@Column(length=10)
	private Integer sort;//排序
	@Column(length=10)
	private Integer searchHit; //搜索次数 
	@Column(length=10)
	private Integer hot;//是否显示  热门
	
	
	
	@Column(precision = 10, scale = 2)
	private  BigDecimal price;//价 格  
	@Column(length=200)
	private  String fileUrl;//文档
	
	
	
	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public Integer getSearchHit() {
		return searchHit;
	}

	public void setSearchHit(Integer searchHit) {
		this.searchHit = searchHit;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public VenueType getVenueType() {
		return venueType;
	}

	public void setVenueType(VenueType venueType) {
		this.venueType = venueType;
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getClickHit() {
		return clickHit;
	}

	public void setClickHit(Integer clickHit) {
		this.clickHit = clickHit;
	}

	public Integer getReplyHit() {
		return replyHit;
	}

	public void setReplyHit(Integer replyHit) {
		this.replyHit = replyHit;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	 
	
	
	
	
}
