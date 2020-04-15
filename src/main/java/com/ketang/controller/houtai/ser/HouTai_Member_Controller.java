package com.ketang.controller.houtai.ser;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.base.UserDao;
import com.ketang.dao.ser.MemberDao;
import com.ketang.entity.base.Role;
import com.ketang.entity.base.User;
import com.ketang.entity.ser.Member;
import com.ketang.service.base.RoleService;

@Controller
@RequestMapping("/houtai/member")
public class HouTai_Member_Controller {
	
	@Resource
	private MemberDao memberDao  ;
	@Resource
	private RoleService roleService;
	
	/**
	 * /houtai/member/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/member/member_manage");
		return mav;
	}
	/**
	 * /houtai/member/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Member member   = memberDao.findId(id);
		mav.addObject("member", member);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/member/update?id=" + id);
		mav.setViewName("/admin/page/member/add_update");
		return mav;
	}
	
	/**
	 * /houtai/member/set_new_pwd?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/set_new_pwd")
	public ModelAndView set_new_pwd(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Member member = memberDao.findId(id);
		mav.addObject("member", member);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/member/update?id=" + id);
		mav.setViewName("/admin/page/member/set_new_pwd");
		return mav;
	}
	
	
	/**
	 * /houtai/member/recharge?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recharge")
	public ModelAndView recharge(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Member member = memberDao.findId(id);
		mav.addObject("member", member);
		mav.setViewName("/admin/page/member/recharge.html");
		return mav;
	}
	
	
	
}
