package com.ketang.controller.houtai.ser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ketang.entity.ser.Venue;

//报表

@Controller
@RequestMapping("/houtai/chart")
public class HouTai_Chart_Controller {
	
	
	/**
	 * /houtai/chart/click
	 */
	@RequestMapping("/click")
	public ModelAndView click() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/chart/click.html");
		return mav;
	}
	
}
