package com.ketang.controller.houtai.ser;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/houtai/my/coupon")
public class HouTai_MyCoupon_Controller {
	
	
	/**
	 * /houtai/my/coupon/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/my_coupon/my_coupon_manage.html");
		return mav;
	}
	
}

