package com.ketang.controller.houtai.ser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/houtai/note")
public class HouTai_Note_Controller {
	
	
	/**
	 * /houtai/note/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/note/note_manage");
		return mav;
	}
	
	
	
}
