package com.ketang.controller.houtai.ser;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.CouponDao;
import com.ketang.dao.ser.LunBoDao;
import com.ketang.entity.ser.Coupon;
import com.ketang.entity.ser.LunBo;
import com.ketang.service.ser.LunBoService;

@Controller
@RequestMapping("/houtai/coupon")
public class HouTai_Coupon_Controller {
	@Resource
	private CouponDao couponDao   ;
	
	/**
	 * /houtai/coupon/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/coupon/coupon_manage");
		return mav;
	}
	
	/**
	 * /houtai/coupon/add
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/coupon/add");
		mav.setViewName("/admin/page/coupon/add_update");
		return mav;
	}
	
	/**
	 * /houtai/coupon/edit?id=1
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Coupon coupon   = couponDao.findId(id);
		mav.addObject("coupon", coupon);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/coupon/update?id=" + id);
		mav.setViewName("/admin/page/coupon/add_update");
		return mav;
	}
	
	
}
