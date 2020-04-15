package com.ketang.controller.pc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.CollectionDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.dao.ser.VenueTypeDao;
import com.ketang.entity.ser.Collection;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.ser.VenueService;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@Controller
public class VenueType_Controller {
	
	@Resource
	private CollectionDao collectionDao ;
	@Resource
	private VenueDao venueDao ;
	@Resource
	private VenueTypeDao venueTypeDao   ;
	@Resource
	private VenueService venueService      ;
	
	/**
	 *  /venue/type/1
	 * @return springmvc会自动把这个id提出来 /a/shop/1   会自动 提出1当id
	 * @throws Exception
	 */
	@RequestMapping("/venue/type/{id}")
	public ModelAndView venue(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		VenueType venueType      = venueTypeDao.findId(id);
		mav.addObject("venueType", venueType);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("venueType", venueType);
		List<Venue> venueList = venueService.list(map , 0,100) ;
		mav.addObject("venueList", venueList);
		
		mav.setViewName("/pc/venue_type/index.html");
		return mav;
	}
	
	
	/**
	 *  #图表数据接口
	 *  /api/venue/type/data
	 */
	@ResponseBody
	@RequestMapping("/api/venue/type/data")
	public List<JSONObject> data()throws Exception {
		
		List<JSONObject> list = new ArrayList<JSONObject>() ;
		
		Pageable pageable= PageRequest.of(0, 100, Sort.Direction.ASC,"sort");
		Page<VenueType> pagelist = venueTypeDao.findAll(pageable);
		List<VenueType> venueTypeList = pagelist.getContent();//拿到list集合
		
		for(VenueType type:venueTypeList) {
			JSONObject result = new JSONObject();
			result.put("name", type.getName());
			//计算收藏和 点击数量
			List<Venue> venues = venueDao.findByVenueType(type);
			Integer collection = 0 ;
			Integer click = 0 ;
			for(Venue venue:venues) {
				click = click+ venue.getClickHit();
				collection = collection+ collectionDao.getTotalByVenue(venue.getId());
			}
			
			result.put("collection", collection);
			result.put("click", click);
			list.add(result);
		}
		 
		return list;
	}
	
	
	
	
}
