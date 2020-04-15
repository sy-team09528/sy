package com.ketang.controller.admin.ser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ketang.dao.ser.ChatDao;
import com.ketang.dao.ser.ReplyDao;
import com.ketang.entity.ser.Chat;
import com.ketang.entity.ser.Link;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.ChatService;
import com.ketang.service.ser.MemberService;
import com.ketang.service.ser.ReplyService;
import com.ketang.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/chat")
public class Admin_Chat_Controller {
	
	@Resource
	private ChatService chatService;
	
	@Resource
	private ChatDao chatDao ;
	
	
	
	
	/**
	 * /admin/chat/list
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "venueId", required = false) Integer venueId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(venueId!=null) {
			Venue venue = new Venue();
			venue.setId(venueId);
			map.put("venue", venue);
		}
		
		if(StringUtil.isNotEmpty(q))
			map.put("q", q);
		
		List<Chat> list = chatService.list(map, page-1, limit);
		Long total = chatService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	/**
	 * /admin/chat/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			chatDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		
		result.put("success", true);
		return result;
	}
	
	
	
	
}
