package com.ketang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ketang.dao.base.MenuDao;
import com.ketang.dao.ser.HotWordDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.base.Config;
import com.ketang.entity.base.Menu;
import com.ketang.entity.base.RoleMenu;
import com.ketang.entity.base.User;
import com.ketang.entity.ser.Coupon;
import com.ketang.entity.ser.HotWord;
import com.ketang.entity.ser.LunBo;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.base.MenuService;
import com.ketang.service.base.RoleMenuService;
import com.ketang.service.ser.CouponService;
import com.ketang.service.ser.LunBoService;
import com.ketang.service.ser.VenueService;
import com.ketang.service.ser.VenueTypeService;
import com.ketang.util.BrowserUtil;
import net.sf.json.JSONObject;


@Controller
public class IndexController {
	
	@Resource
	private RoleMenuService roleMenuService;
	@Resource
	private MenuService menuService;
	@Resource
	private MenuDao menuDao;
	@Resource
	private HotWordDao hotWordDao;
	@Resource
	private VenueTypeService venueTypeService;
	@Resource
	private VenueService venueService;
	@Resource
	private VenueDao venueDao ;
	@Resource
	private LunBoService lunBoService ;
	
	
	@RequestMapping("/hot")
	public ModelAndView  hot(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/hot.html");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hot",1);
		map.put("state",1);
		List<Venue> venueList= venueService.list(map , 0, 100);
		mav.addObject("venueList", venueList);
		
		mav.addObject("title", "近期热门");
		return mav;
	}
	
	
	
	/**
	 * #/search?q=23
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public ModelAndView  search(@RequestParam(value = "q", required = false) String q) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/search.html");
		
		
		Venue  venue  = venueDao.findByTitle(q);
		if(venue!=null) {
			venue.setSearchHit(venue.getSearchHit()+1);
			venueDao.save(venue);
		}
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("q",q);
		List<Venue> venueList= venueService.list(map , 0, 100);
		mav.addObject("venueList", venueList);
		
		mav.addObject("title", "我的搜索");
		return mav;
	}
	
	
	
	/**
	 * 请求首页
	 */
	@RequestMapping("/")
	public ModelAndView  index_1(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/index");
		
		Pageable pageable= PageRequest.of(0, 100, Sort.Direction.ASC,"sort");
		Page<HotWord> pageList = hotWordDao.findAll(pageable);
		List<HotWord> hotWordList = pageList.getContent();//拿到list集合
		mav.addObject("hotWordList", hotWordList);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("useIt",1);
		List<VenueType> typeList= venueTypeService.list(map , 0, 100);
		mav.addObject("typeList", typeList);
		
		map.put("state",1);
		map.put("top",1);
		List<Venue> venueList= venueService.list(map , 0, 100);
		mav.addObject("venueList", venueList);
		
		
		map.put("useIt", 1);
		List<LunBo> lunboList = lunBoService.list(map , 0, 50);
		mav.addObject("lunboList", lunboList);
		
		
		mav.addObject("title", "首页");
		return mav;
	}
	
	/**
	 * 请求首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/index");
		
		Pageable pageable= PageRequest.of(0, 100, Sort.Direction.ASC,"sort");
		Page<HotWord> pageList = hotWordDao.findAll(pageable);
		List<HotWord> hotWordList = pageList.getContent();//拿到list集合
		mav.addObject("hotWordList", hotWordList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("useIt",1);
		List<VenueType> typeList= venueTypeService.list(map , 0, 100);
		mav.addObject("typeList", typeList);
		
		map.put("state",1);
		map.put("top",1);
		List<Venue> venueList= venueService.list(map , 0, 100);
		mav.addObject("venueList", venueList);
		
		
		map.put("useIt", 1);
		List<LunBo> lunboList = lunBoService.list(map , 0, 50);
		mav.addObject("lunboList", lunboList);
		
		
		mav.addObject("title", "首页");
		return mav;
	}
	
	/**
	 * 电脑登陆
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/login/login");
		return mav;
	}
	
	/**
	 * 联系我们
	 */
	@RequestMapping("/contact")
	public ModelAndView contact(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "联系我们");
		mav.setViewName("/pc/contact.html");
		return mav;
	}
	
	
	/**
	 * 后台主页
	 */
	@RequestMapping("/admin/main")
	public ModelAndView admin_main(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		 
		mav.setViewName("/admin/main");
		
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser"); 
		if(currentUser.getRole()==null){
			return mav;
		}
		
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", -1);
		List<Menu> menuList = menuService.list(map , 0, 200);
		for (Menu menu : menuList) {
			RoleMenu roleMenu=	roleMenuService.findByRoleIdAndMenuId(currentUser.getRole().getId(), menu.getId());
			if(roleMenu!=null){
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("text", menu.getName());
				node.put("url", menu.getUrl());
				node.put("type", menu.getType());
				node.put("icon", menu.getIcon());
				node.put("children", getChildren(menu.getId(),currentUser.getRole().getId()));
				list.add(node);
			}
		}
		mav.addObject("treeList", list);
		return mav;
	}
	
	/**
	 * 辅助方法  辅助 上面的 admin_main（getCheckedTreeMenu）
	 * @param pId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getChildren(Integer pId, Integer roleId)  throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", pId);
		List<Menu> menuList = menuService.list(map , 0, 100);
		
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		for (Menu menu : menuList) {
			RoleMenu roleMenu=	roleMenuService.findByRoleIdAndMenuId(roleId, menu.getId());
			if(roleMenu!=null){
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("text", menu.getName());
				node.put("url", menu.getUrl());
				node.put("type", menu.getType());
				node.put("icon", menu.getIcon());
				list.add(node);
			}
		}
		return list;
	}
	
	 
}
