package com.ketang.controller.admin.ser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ketang.dao.ser.ChapterDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.ChapterService;
import com.ketang.service.ser.VenueService;

import net.sf.json.JSONObject;

//报表

@Controller
@RequestMapping("/admin/chart")
public class Admin_Chart_Controller {

	@Resource
	private VenueService venueService  ;
	@Resource
	private VenueDao venueDao  ;
	
	/**
	 * /admin/chart/click/data
	 */
	@ResponseBody
	@RequestMapping("/click/data")
	public List<Venue> click_data() throws Exception {
		List<Venue> venues  = venueDao.findAll();
		return venues;
	}
	
	
	
}
