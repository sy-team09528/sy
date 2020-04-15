package com.ketang.controller.pc;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.ReplyDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.Venue;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/reply")
public class Reply_Controller {
	
	
	@Resource
	private ReplyDao replyDao    ;
	@Resource
	private VenueDao venueDao ;
	
	
	/**
	 * # 
	 * /reply/api/add
	 */
	@ResponseBody
	@RequestMapping("/api/add")
	public JSONObject api_add(Reply reply , HttpSession session)throws Exception {
		JSONObject result = new JSONObject();
		
		//判断 用户
		Member member =(Member) session.getAttribute("member");
		if(member==null) {
			result.put("success", false);
			result.put("msg", "请先登录用户");
		}else {
			Venue venue = venueDao.findId(reply.getVenue().getId());
			venue.setReplyHit(venue.getReplyHit()+1);
			venueDao.save(venue);
			
			reply.setMember(member);
			reply.setCreateDateTime(new Date());
			replyDao.save(reply);
			result.put("success", true);
		}
		return result;
	}
	
	
}
