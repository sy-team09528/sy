package com.ketang.controller.pc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.CollectionDao;
import com.ketang.entity.ser.Chat;
import com.ketang.entity.ser.Collection;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.CollectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/collection")
public class Collection_Controller {
	
	@Resource
	private CollectionDao  collectionDao   ;
	@Resource
	private CollectionService  collectionService   ;
	
	
	/**
	 *  /collection/home
	 * @return springmvc 会自动把这个id提出来 /a/shop/1    会自动 提出1当id
	 * @throws Exception
	 */
	@RequestMapping("/home")
	public ModelAndView home(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Member member = (Member) session.getAttribute("member");
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			mav.addObject("title" , "我的收藏");
			mav.setViewName("/pc/collection/home.html");
		}
		return mav;
	}
	
	
	/**
	 * #添加收藏  接口
	 * /collection/api/add
	 */
	@ResponseBody
	@RequestMapping("/api/add")
	public JSONObject api_add(Collection collection , HttpSession session)throws Exception {
		JSONObject result = new JSONObject();
		
		//判断 用户
		Member member =(Member) session.getAttribute("member");
		if(member==null) {
			result.put("success", false);
			result.put("msg", "请先登录用户");
		}else {
			//查询是否收藏。
			Collection temp = collectionDao.findByMember_Venue(member.getId(), collection.getVenue().getId());
			if(temp!=null) {
				result.put("success", false);
				result.put("msg", "已收藏");
				return result;
			}
			//查询是否收藏。
			collection.setMember(member);
			collection.setCreateDateTime(new Date());
			collectionDao.save(collection);
			result.put("success", true);
		}
		return result;
	}
	
	/**
	 * # 根据  场馆id 加载聊天数据
	 *   /collection/api/list
	 */
	@ResponseBody
	@RequestMapping("/api/list")
	public Map<String, Object>  api_list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,HttpSession session)throws Exception {
		Member member = (Member) session.getAttribute("member");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		List<Collection> list = collectionService.list(map , page-1, limit);
		Long total = collectionService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
		
	}
	
	
	
	/**
	 * /collection/api/delete
	 */
	@ResponseBody
	@RequestMapping("/api/delete")
	public JSONObject api_delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		
		for (int i = 0; i < idsStr.length; i++) {
			try {
				collectionDao.deleteById(Integer.parseInt(idsStr[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		result.put("success", true);
		return result;
	}
	
	
	
	/**
	 * #  
	 *   /collection/api/check?memberId=12
	 */
	@ResponseBody
	@RequestMapping("/api/check")
	public JSONObject api_check(@RequestParam(value = "memberId", required = false) Integer memberId,
			@RequestParam(value = "venueId", required = false) Integer venueId
			)throws Exception {
		JSONObject result = new JSONObject();
		
		Integer total    = collectionDao.getTotal_venueId_memberId(venueId, memberId) ;
		
		if(total>0) {
			result.put("success", true);
			result.put("msg", "已收藏");
		}else {
			result.put("success", false);
			result.put("msg", "没有收藏");
		}
		
		return result;
		
	}
	
	
	
}
