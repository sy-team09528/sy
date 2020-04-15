package com.ketang.controller.pc;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.SignInDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.SignIn;
import com.ketang.util.DateUtil;

import net.sf.json.JSONObject;

@Controller
public class SignIn_Controller {
	
	@Resource
	private SignInDao signInDao ;
	@Resource
	private MemberDao memberDao ;
	
	
	/**
	 *  /sign/in/check
	 */
	@ResponseBody
	@RequestMapping("/sign/in/check")
	public JSONObject sign_in_check(HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		Member member =(Member) session.getAttribute("member");	
		String date = DateUtil.formatDate(new Date() , "yyyy-MM-dd");
		
		List<SignIn> signInList  = signInDao.findByDateAndMember(date, member.getId());
		if(signInList.size()>0) {
			result.put("success", false);
			result.put("msg", "今日已签到");
		}else {
			result.put("success", true);
			result.put("msg", "签到+1积分");
		}
		return result;
	}
	
	/**
	 *  /sign/in/add
	 */
	@ResponseBody
	@RequestMapping("/sign/in/add")
	public JSONObject sign_in_add(HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		Member member =(Member) session.getAttribute("member");	
		
		SignIn signIn = new SignIn();
		signIn.setCreateDate(new Date());
		signIn.setMember(member);
		signInDao.save(signIn);
		
		member = memberDao.findId(member.getId());
		member.setIntegral(member.getIntegral()+1);
		memberDao.save(member);
		
		result.put("success", true);
		result.put("msg", "签到成功");
		
		return result;
	}
	
	
}
