package com.ketang.controller.houtai.ser;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.VenueTypeDao;
import com.ketang.entity.ser.VenueType;

@Controller
@RequestMapping("/houtai/venue/type")
public class HouTai_VenueType_Controller {
	
	@Resource
	private VenueTypeDao venueTypeDao ;
	
	/**
	 *  /houtai/venue/type/manage
	 * 
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/venue_type/venue_type_manage");
		return mav;
	}
	
	/**
	 * /houtai/venue/type/add
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/venue/type/add");
		mav.setViewName("/admin/page/venue_type/add_update");
		return mav;
	}
	
	/**
	 * /houtai/venue/type/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		VenueType venueType   = venueTypeDao.findId(id);
		mav.addObject("venueType", venueType);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/venue/type/update?id=" + id);
		mav.setViewName("/admin/page/venue_type/add_update");
		return mav;
	}
	
	
}
