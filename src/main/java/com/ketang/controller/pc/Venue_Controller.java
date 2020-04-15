package com.ketang.controller.pc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.OrderDao;
import com.ketang.dao.ser.ReplyDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Link;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueRecord;
import com.ketang.service.ser.ChapterService;
import com.ketang.service.ser.ReplyService;
import com.ketang.service.ser.VenueRecordService;
import com.ketang.service.ser.VenueService;
import com.ketang.util.DateUtil;

import javassist.expr.NewArray;
import net.sf.json.JSONObject;

@Controller
public class Venue_Controller {
	
	@Resource
	private OrderDao orderDao   ;
	@Resource
	private VenueDao venueDao ;
	@Resource
	private VenueRecordService  venueRecordService ;
	@Resource
	private ReplyDao replyDao ;
	@Resource
	private ReplyService replyService ;
	@Resource
	private ChapterService chapterService   ;
	
	/**
	 *  /venue/1
	 * @return springmvc会自动把这个id提出来 /a/shop/1   会自动 提出1当id
	 * @throws Exception
	 */
	@RequestMapping("/venue/{id}")
	public ModelAndView venue(@PathVariable("id") Integer id,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		Venue venue      = venueDao.findId(id);
		mav.addObject("venue", venue);
		
		//添加点击次数
		venue.setClickHit(venue.getClickHit()+1);
		venueDao.save(venue);
		//添加点击次数
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("venue", venue);
		List<Reply> replyList = replyService.list(map , 0,100) ;
		mav.addObject("replyList", replyList);
		
		//判断 用户有没有购买这个课程。
		Member member  =  (Member) session.getAttribute("member");
		if(member==null) {
			//记录 flag = false  没有购买
			mav.addObject("flag", false);
		}else {
			Integer total   = orderDao.find_memberId_venueId(member.getId(), venue.getId());
			System.out.println(total);
			if(total==0) {
				//记录 flag = false  没有购买
				mav.addObject("flag", false);
			}else {
				//记录 flag = false  没有购买
				mav.addObject("flag", true);
			}
		}
		//判断 用户有没有购买这个课程。
		List<Chapter> chapterList = chapterService.list(map, 0, 500);
		mav.addObject("chapterList", chapterList);
		
		//加载推荐课程 
		List<Venue> tuijianList = venueDao.tuijian();
		mav.addObject("tuijianList", tuijianList);
		//加载推荐课程 
		
		mav.setViewName("/pc/venue/view");
		return mav;
	}
	
	/**
	 * 
	 * # 根据id取得 百度图表数据。多曲线图。
	 * # /api/venue/getEchartsData?id=11
	 */
	@ResponseBody
	@RequestMapping("/api/venue/getEchartsData")
	public List<VenueRecord> api_venue_getEchartsData(@RequestParam(value = "venueId", required = false) Integer venueId) throws Exception {
		//num1 收入  num2  支出
		Map<String, Object> map = new HashMap<String, Object>();
		Venue venue = venueDao.findId(venueId);
		map.put("venue", venue);
		
		String currDayStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		String yesterday = DateUtil.dateAddDay(currDayStr, "yyyy-MM-dd", -10);
		
		map.put("date1", yesterday);
		map.put("date2", currDayStr);
		List<VenueRecord> venueRecordList= venueRecordService.list(map , 0, 200);
		System.out.println(venueRecordList.size());
		return venueRecordList;
	}
	
	
	
	
}
