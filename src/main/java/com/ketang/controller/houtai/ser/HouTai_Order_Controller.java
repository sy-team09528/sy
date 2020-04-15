package com.ketang.controller.houtai.ser;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.OrderDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Order;

@Controller
@RequestMapping("/houtai/order")
public class HouTai_Order_Controller {
	@Resource
	private OrderDao orderDao    ;
	
	
	/**
	 * /houtai/order/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/order/order_manage");
		return mav;
	}
	
	/**
	 * /houtai/order/view?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Order order   = orderDao.findId(id);
		mav.addObject("order", order);
		mav.setViewName("/admin/page/order/view");
		return mav;
	}
	
	
}
