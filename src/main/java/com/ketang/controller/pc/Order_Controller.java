package com.ketang.controller.pc;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.MyCouponDao;
import com.ketang.dao.ser.OrderDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.MyCoupon;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.MyCouponService;
import com.ketang.service.ser.OrderService;
import com.ketang.util.BigDecimalUtil;
import com.ketang.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class Order_Controller {
	@Resource
	private VenueDao venueDao  ;
	@Resource
	private OrderService orderService ;
	@Resource
	private OrderDao orderDao   ;
	@Resource
	private MemberDao memberDao       ;
	@Resource
	private MyCouponService myCouponService;
	@Resource
	private MyCouponDao myCouponDao  ;
	
	/**
	 *    #确定订单的页
	 *    /order/create?id=3  前面中商品id 后面是数量
	 */
	@RequestMapping("/order/create")
	public ModelAndView create(@RequestParam(value = "id", required = false) Integer id  ,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		Venue venue = venueDao.findId(id);
		mav.addObject("venue", venue);
		mav.addObject("title", "确认订单");
		
		Member member  = (Member) session.getAttribute("member");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		map.put("state", 0);
		List<MyCoupon> myCouponList = myCouponService.list(map , 0, 100);
		mav.addObject("myCouponList", myCouponList);
		
		mav.setViewName("/pc/order/create.html");
		return mav;
	}
	
	
	/**
	 *  /order/1
	 * @return springmvc会自动把这个id提出来  
	 */
	@RequestMapping("/order/{id}")
	public ModelAndView order_view(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Order order = orderDao.findId(id);
		mav.addObject("title", "订单详情");
		mav.addObject("order", order);
		mav.setViewName("/pc/order/view.html");
		return mav;
	}
	
	
	/**
	 *   /api/order/add
	 *   total_heji
	 *   
	 */
	@ResponseBody
	@RequestMapping("/api/order/add")
	public JSONObject api_order_add(@RequestParam(value = "venueId") Integer venueId
			,@RequestParam(value = "myCouponId") Integer myCouponId
			,@RequestParam(value = "total_heji") BigDecimal total_heji
			,HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		Member member = (Member) session.getAttribute("member") ;
		if(member==null) {
			result.put("success", false);
			result.put("msg", "请先登录.");
			return result;
		}
		
		Order order = new Order();
		Venue venue = venueDao.findId(venueId);
		if(myCouponId!=null) {
			MyCoupon myCoupon = myCouponDao.findId(myCouponId);
			myCoupon.setState(1);
			myCoupon.setUseDateTime(new Date());
			myCouponDao.save(myCoupon);
			order.setMyCoupon(myCoupon);
		}
		
		order.setCreateDateTime(new Date());
		order.setMember(member);
		order.setVenue(venue);
		order.setAmount(total_heji);
		order.setNum(DateUtil.formatDate(new Date(),"yyyyMMddHHmmssSSS"));
		order.setState(0);
		member = memberDao.findId(member.getId());
		orderDao.save(order) ;
		
		result.put("success", true);
		result.put("orderId", order.getId());
		result.put("msg", "订单创建成功.");
		
		return result;
	}
	
	
	
	
	
	
	/**
	 * /api/order/delete
	 */
	@ResponseBody
	@RequestMapping("/api/order/delete")
	public JSONObject api_order_delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			orderDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
	
	/**
	 *   /api/order/pay
	 */
	@ResponseBody
	@RequestMapping("/api/order/pay")
	public JSONObject api_order_pay(@RequestParam(value = "orderId") Integer orderId,HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		Member member = (Member) session.getAttribute("member") ;
		member = memberDao.findId(member.getId());
		if(member==null) {
			result.put("success", false);
			result.put("msg", "请先登录.");
			return result;
		}
		Order order = orderDao.findId(orderId);
		if(member.getBalance().compareTo(order.getAmount())<0) {
			result.put("success", false);
			result.put("msg", "余额不足.");
			return result;
		}
		
		order.setState(1);
		member = memberDao.findId(member.getId());
		member.setBalance(member.getBalance().subtract(order.getAmount()));
		memberDao.save(member);
		orderDao.save(order) ;
		
		result.put("success", true);
		result.put("orderId", order.getId());
		result.put("msg", "付款成功.");
		
		return result;
	}
	
}
