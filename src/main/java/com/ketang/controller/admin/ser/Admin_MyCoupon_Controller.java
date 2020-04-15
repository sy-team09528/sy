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

import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.MyCouponDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.MyCoupon;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.MemberService;
import com.ketang.service.ser.MyCouponService;

@Controller
@RequestMapping("/admin/my/coupon")
public class Admin_MyCoupon_Controller {
	
	@Resource
	private MemberDao memberDao  ;
	@Resource
	private  MyCouponService myCouponService  ;
	@Resource
	private MyCouponDao myCouponDao    ;
	
	/**
	 * /admin/my/coupon/list
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "memberId", required = false) Integer memberId,
			@RequestParam(value = "state", required = false) Integer state,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(state!=null) {
			map.put("state", state);
		}
		if(memberId!=null) {
			Member member = new Member();
			member.setId(memberId);
			map.put("member", member);
		}
		List<MyCoupon> list = myCouponService.list(map, page-1, limit);
		Long total = myCouponService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	
	/**
	 * /admin/my/coupon/findById
	 * #通过Id查找实体
	 * @param id
	 */
	@ResponseBody	
	@RequestMapping("/findById")
	public MyCoupon findById(@RequestParam(value="id")Integer id,HttpServletResponse response)throws Exception{
		MyCoupon myCoupon  =myCouponDao.findId(id);
		return myCoupon;
	}
	
}
