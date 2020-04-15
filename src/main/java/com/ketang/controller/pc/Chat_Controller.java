package com.ketang.controller.pc;



import java.util.Date;
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

import com.ketang.dao.ser.ChatDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Chat;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.ChatService;
import com.ketang.util.StringUtil;

import net.sf.json.JSONObject;



@Controller
public class Chat_Controller {
	
	@Resource
	private ChatDao chatDao ;
	@Resource
	private VenueDao venueDao ;
	@Resource
	private ChatService chatService;
	
	
	
	/**
	 *  /chat/1  这个id是venueid
	 * @return springmvc 会自动把这个id提出来 /a/shop/1    会自动 提出1当id
	 * @throws Exception
	 */
	@RequestMapping("/chat/{id}")
	public ModelAndView chat(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Venue venue   = venueDao.findId(id);
		mav.addObject("venue", venue);
		mav.setViewName("/pc/chat/chat.html");
		return mav;
	}
	
	/**
	 *  /chat/home
	 */
	@RequestMapping("/chat/home")
	public ModelAndView chat_home(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		Member member = (Member) session.getAttribute("member");
		
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			mav.addObject("title", "我的聊天");
			mav.setViewName("/pc/chat/home.html");
		}
		
		return mav;
	}
	
	
	
	/**
	 * # 回复 聊天 接口
	 * /member/add
	 */
	@ResponseBody
	@RequestMapping("/api/chat/add")
	public JSONObject api_chat_add(Chat chat , HttpSession session )throws Exception {
		JSONObject result = new JSONObject();
		chat.setCreateDateTime(new Date());
		
		Member  member = (Member) session.getAttribute("member");
		if(member==null) {
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}else {
			chat.setMember(member);chatDao.save(chat) ;
			result.put("success", true);
		}
		return result;
	}
	
	/**
	 * # 根据  场馆id 加载聊天数据
	 * /member/add
	 */
	@ResponseBody
	@RequestMapping("/api/chat/list")
	public List<Chat> api_chat_list(@RequestParam(value = "venueId", required = false) Integer venueId )throws Exception {
		return chatDao.findByVenueId(venueId);
	}
	
	
	
	/**
	 * /api/chat/list1
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/api/chat/list1")
	public Map<String, Object> api_chat_list1(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,HttpSession session) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Member member = (Member) session.getAttribute("member");
		
		if(member!=null) {
			map.put("member", member);
		}
		
		List<Chat> list = chatService.list(map, page-1, limit);
		Long total = chatService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	
}
