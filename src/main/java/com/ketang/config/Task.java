package com.ketang.config;


import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.DaemonThreadFactory;
import com.ketang.dao.ser.VenueDao;
import com.ketang.dao.ser.VenueRecordDao;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueRecord;
import com.ketang.util.DateUtil;


@Component
@Configurable
@EnableScheduling
public class Task  {
	
	@Resource
	private VenueDao venueDao;
	@Resource
	private VenueRecordDao venueRecordDao  ;
	
	
	/**
	 *  每秒  输出一次
	 */
	@Scheduled(cron="0/3 * * * * ?")
	public void test() {
		//System.out.println("我是定时器，");
	}
	
	
	/**
	 * # 每天  清0
	 */
	@Scheduled(cron="0 59 23 * * ?")
	public void record() {
		List<Venue> venues = 	venueDao.findAll();
		for(Venue venue :venues) {
			//拿现在的  减去 上一天的。 就是今天的。
			//当天日期减一
			String currDayStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
			String yesterday = DateUtil.dateAddDay(currDayStr, "yyyy-MM-dd", -1);
			VenueRecord yesterDayVenueRecord = venueRecordDao.findDateVenue(venue.getId(), yesterday);
			
			VenueRecord venueRecord = new VenueRecord();
			venueRecord.setVenue(venue);
			venueRecord.setCreateDate(new Date());
			
			if(yesterDayVenueRecord!=null) {
				venueRecord.setClickHit(venue.getClickHit()-yesterDayVenueRecord.getClickHit());
				venueRecord.setSearchHit(venue.getSearchHit()-yesterDayVenueRecord.getSearchHit());
			}else {
				System.out.println("没有");
				venueRecord.setClickHit(venue.getClickHit());
				venueRecord.setSearchHit(venue.getSearchHit());
			}
			
			venueRecordDao.save(venueRecord);
			
		}
	}
	
	
}
