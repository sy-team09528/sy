package com.ketang.controller.houtai.ser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.CharacterData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.ChapterDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;




@Controller
@RequestMapping("/houtai/chapter")
public class HouTai_Chapter_Controller {
	@Resource
	private VenueDao venueDao ;
	@Resource
	private ChapterDao chapterDao   ;
	
	
	/**
	 * /houtai/chapter/manage?venueId=12
	 */
	@RequestMapping("/manage")
	public ModelAndView manage(@RequestParam(value = "venueId", required = false) Integer venueId) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Venue venue = venueDao.findId(venueId);
		mav.addObject("venue", venue);
		
		mav.addObject("title", venue.getTitle()+"，的课程章节");
		
		mav.setViewName("/admin/page/chapter/chapter_manage.html");
		return mav;
	}
	
	/**
	 * /houtai/chapter/add
	 */
	@RequestMapping("/add")
	public ModelAndView add(@RequestParam(value = "venueId", required = false) Integer venueId) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("btn_text", "提交");
		mav.addObject("state", 0);
		mav.addObject("venueId", venueId);
		
		mav.addObject("save_url", "/admin/chapter/add");
		mav.addObject("isEdit", false);
		mav.setViewName("/admin/page/chapter/add_update.html");
		return mav;
	}
	
	/**
	 * /houtai/chapter/edit?id=1
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Chapter chapter     = chapterDao.findId(id);
		mav.addObject("chapter", chapter);
		mav.addObject("isEdit", true);

		mav.addObject("venueId", chapter.getVenue().getId());
		
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/chapter/update?id=" + id);
		mav.setViewName("/admin/page/chapter/add_update.html");
		return mav;
	}
	
	
	
	
	
}
