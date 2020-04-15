package com.ketang.controller.pc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.CouponDao;
import com.ketang.dao.ser.MyCouponDao;
import com.ketang.entity.ser.Coupon;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.MyCoupon;
import com.ketang.service.ser.CouponService;
import net.sf.json.JSONObject;


@Controller
public class Coupon_Controller {
	
	@Resource
	private CouponService couponService ;
	@Resource
	private CouponDao  couponDao ;
	@Resource
	private  MyCouponDao  myCouponDao ;
	
	/**
	 * @throws Exception
	 */
	@RequestMapping("/coupon/index")
	public ModelAndView coupon_index() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "优惠券中心");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Coupon> couponList = couponService.list(map , 0, 222);
		mav.addObject("couponList", couponList);
		
		mav.setViewName("/pc/coupon/index.html");
		return mav;
	}
	
	
	/**
	 * @throws Exception
	 */
	@RequestMapping("/member/coupon")
	public ModelAndView member_coupon(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Member member = (Member) session.getAttribute("member");
		
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			mav.addObject("title", "我的优惠券");
			mav.setViewName("/pc/coupon/member_coupon.html");
		}
		
		return mav;
	}
	
	
	
	/**
	 * # 
	 *  /api/coupon/add
	 */
	@ResponseBody
	@RequestMapping("/api/coupon/add")
	public JSONObject api_add(@RequestParam(value = "id", required = false) Integer id, HttpSession session)throws Exception {
		JSONObject result = new JSONObject();
		
		//判断 用户
		Member member =(Member) session.getAttribute("member");
		if(member==null) {
			result.put("success", false);
			result.put("msg", "请先登录用户");
			return result;
		}else {
			Coupon coupon = couponDao.findId(id);
			if(coupon.getNum()<=0) {
				result.put("success", false);
				result.put("msg", "数量为0，领取失败");
				return result;
			}else {
				
				MyCoupon myCoupon = new MyCoupon();
				myCoupon.setName(coupon.getName());
				myCoupon.setJine(coupon.getJine());
				myCoupon.setState(0);
				myCoupon.setCreateDateTime(new Date());
				myCoupon.setMember(member);
				myCouponDao.save(myCoupon);
				
				coupon.setNum(coupon.getNum()-1);
				couponDao.save(coupon);
				
				result.put("success", true);
				return result;
			}
		}
	}
	
	
}
