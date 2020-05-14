package com.ketang.entity.ser;

import java.util.Date;
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
import com.ketang.entity.base.CustomDateTimeSerializer;

/**
 * 收藏的实体类
 *      配置映射关系
 *
 *      1.实体类和表的映射关系
 *          @Entity:声明实体类
 *          @Table:配置实体类和表的映射关系
 *              name:配置数据库表的名称
 *      2.实体类中属性和表中字段的映射关系
 *
 */

@Entity  //声明实体类
@Table(name = "t_collection")  //建立实体类和表的映射关系
public class Collection {

	/**
	 * @Id:生成主键的配置
	 * @GeneratedValue:配置主键的生成策略
	 *      1.GenerationType.IDENTITY: 自增，mysql
	 *          * 底层数据库必须支持自动增长（底层数据库支持的自动增长方式，对id自增)
	 *      2.GenerationType.SEQUENCE: 序列，oracle
	 *          * 底层数据库必须支持序列
	 *      3.GenerationType.TABLE: jpa提供的一种机制，通过一张数据库表的形式帮助我们完成主键自增
	 *      4.GenerationType.AUTO: 由程序自动的帮助我们选择主键生成策略
	 * @Column:配置属性和字段的映射关系
	 *      name:数据库表中字段名称
	 */

	@Id   //声明当前私有属性为主键
	@GeneratedValue(strategy = GenerationType.IDENTITY)   //配置主键的生成策略  IDENTITY-->自增
	private Integer id;
	
	@OneToOne   //配置一对一关系
	@JoinColumn(name="venueId")  //配置外键
	private Venue venue ; // 课程
	
	@OneToOne
	@JoinColumn(name="memberId")
	private Member member  ; //会员

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;//收藏时间


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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	
	
	
	
	
	
}
