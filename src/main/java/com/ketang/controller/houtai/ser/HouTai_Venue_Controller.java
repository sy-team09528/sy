package com.ketang.controller.houtai.ser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.ser.VenueTypeService;


@Controller
@RequestMapping("/houtai/venue")
public class HouTai_Venue_Controller {

	@Resource
	private VenueDao venueDao ;
	@Resource
	private VenueTypeService venueTypeService   ;
	
	/**
	 * /houtai/venue/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<VenueType> venueTypeList = venueTypeService.list(map , 0, 100);
		mav.addObject("venueTypeList",venueTypeList);
		
		mav.setViewName("/admin/page/venue/venue_manage.html");
		return mav;
	}
	/**
	 * /houtai/venue/video/manage
	 */
	@RequestMapping("/video/manage")
	public ModelAndView video_manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<VenueType> venueTypeList = venueTypeService.list(map , 0, 100);
		mav.addObject("venueTypeList",venueTypeList);
		
		mav.setViewName("/admin/page/venue/venue_video_manage.html");
		return mav;
	}
	
	
	
	
	/**
	 * /houtai/venue/add
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<VenueType> venueTypeList = venueTypeService.list(map , 0, 100);
		mav.addObject("venueTypeList",venueTypeList);
		
		
		mav.addObject("title", "添加课程");
		mav.addObject("btn_text", "提交");
		mav.addObject("state", 0);
		mav.addObject("save_url", "/admin/venue/add");
		mav.addObject("isEdit", false);
		mav.setViewName("/admin/page/venue/add_update.html");
		return mav;
	}
	
	/**
	 * /houtai/venue/edit?id=1
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		

		Map<String, Object> map = new HashMap<String, Object>();
		List<VenueType> venueTypeList = venueTypeService.list(map , 0, 100);
		mav.addObject("venueTypeList",venueTypeList);
		
		
		Venue venue     = venueDao.findId(id);
		mav.addObject("venue", venue);
		mav.addObject("title", "修改课程");
		
		mav.addObject("isEdit", true);
		
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/venue/update?id=" + id);
		mav.setViewName("/admin/page/venue/add_update.html");
		return mav;
	}
	
	
	
}
