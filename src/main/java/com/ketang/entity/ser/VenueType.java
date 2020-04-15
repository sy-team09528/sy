package com.ketang.entity.ser;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ketang.entity.base.CustomDateTimeSerializer;


@Entity
@Table(name="t_a_venue_type")
public class VenueType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="类型名称不能为空！")
	@Column(length=50)
	private String name; // 博客类型名称
	
	@Column(length=11)
	private Integer useIt; //是否显示   0     1 
	
	@NotNull(message="排序号不能为空！")
	@Column(length=10)
	private Integer sort;
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//创建时间
	
	
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updateDateTime;//修改时间
	
	@Column(length=150)
	private  String imageUrl;//封面
	
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public Integer getUseIt() {
		return useIt;
	}
	public void setUseIt(Integer useIt) {
		this.useIt = useIt;
	}
	
	
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	
	
	
	
	
}
