package com.ketang.controller.houtai.ser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/houtai/post")
public class HouTai_Post_Controller {
	
	/**
	 * /houtai/post/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/post/post_manage.html");
		return mav;
	}
	
	
	
	
}
