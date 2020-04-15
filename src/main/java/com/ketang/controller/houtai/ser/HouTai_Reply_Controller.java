package com.ketang.controller.houtai.ser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.ReplyService;
import com.ketang.service.ser.VenueService;

@Controller
@RequestMapping("/houtai/reply")
public class HouTai_Reply_Controller {
	

	@Resource
	private VenueService venueService  ;
	
	/**
	 * /houtai/reply/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Venue> venueList = venueService.list(map , 0, 100);
		mav.addObject("venueList", venueList);
		
		
		mav.setViewName("/admin/page/reply/reply_manage");
		return mav;
	}
	
}
