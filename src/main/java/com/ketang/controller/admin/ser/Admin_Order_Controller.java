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
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Order;
import com.ketang.service.ser.MemberService;
import com.ketang.service.ser.OrderService;

@Controller
@RequestMapping("/admin/order")
public class Admin_Order_Controller {

	@Resource
	private MemberDao memberDao;
	@Resource
	private OrderService orderService;

	/**
	 * /admin/order/list
	 * @param page  默认1
	 * @param limit 数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "memberId", required = false) Integer memberId, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (memberId != null) {
			Member member = new Member();
			member.setId(memberId);
			map.put("member", member);
		}
		if (state != null) {
			map.put("state", state);
		}
		List<Order> list = orderService.list(map, page - 1, limit);
		Long total = orderService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}

}
