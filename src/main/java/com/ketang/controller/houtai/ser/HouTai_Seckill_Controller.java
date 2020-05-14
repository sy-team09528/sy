package com.ketang.controller.houtai.ser;


import com.ketang.dao.ser.SeckillDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Seckill;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.ser.VenueTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/houtai/seckill")
public class HouTai_Seckill_Controller {

	@Autowired
	private SeckillDao seckillDao;
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
		mav.setViewName("/admin/page/seckill/seckill_manage.html");
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
		mav.addObject("save_url", "/admin/seckill/add");
		mav.addObject("title", "添加课程");
		mav.addObject("btn_text", "添加");
		mav.addObject("state", 0);
		mav.addObject("isEdit", false);
		mav.setViewName("/admin/page/seckill/add_update.html");
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
		Seckill seckill = seckillDao.findId(id);
		mav.addObject("seckill", seckill);
		mav.addObject("title", "修改秒杀");
		mav.addObject("isEdit", true);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/seckill/update?id=" + id);
		mav.setViewName("/admin/page/seckill/add_update.html");
		return mav;
	}
	
	
	
}
