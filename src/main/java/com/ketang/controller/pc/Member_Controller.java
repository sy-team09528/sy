package com.ketang.controller.pc;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.MyCouponDao;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.MyCoupon;
import com.ketang.service.ser.MemberService;
import com.ketang.util.Base64Util;
import com.ketang.util.DateUtil;
import com.ketang.util.FileUtil;
import com.ketang.util.HttpClient;
import com.ketang.util.MyUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/member")
public class Member_Controller {
	
	@Resource
	private MemberDao memberDao  ;
	@Resource
	private MyCouponDao myCouponDao    ;
	
	@Resource
	private MemberService memberService  ;
	/**
	 * #用户登录页面
	 * /member/login
	 */
	@RequestMapping("/login")
	public ModelAndView login() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/login.html");
		mav.addObject("title", "登录");
		return mav;
	}
	/**
	 * #登录会员 接口
	 * /member/check
	 */
	@ResponseBody
	@RequestMapping("/api/login")
	public JSONObject api_login( @RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "pwd", required = false) String pwd,  HttpSession session)throws Exception {
		JSONObject result = new JSONObject();
		
		//判断 帐号是否存在
		List<Member> members = memberDao.findByName(name);
		if(members.size()==0) {
			result.put("success", false);
			result.put("msg", "帐号不存在");
			return result;
		}
		//判断 帐号是否存在
		Member member = members.get(0);
		
		if(member.getPwd().equals(pwd)) {
			result.put("success", true);
			result.put("msg", "登录成功");
			session.setAttribute("member", member);
		}else {
			result.put("success", false);
			result.put("msg", "密码不对");
		}
		
		return result;
	}
	
	
	/**
	 * #用户注册页面
	 * /member/reg  
	 */
	@RequestMapping("/reg")
	public ModelAndView reg() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/pc/member/reg.html");
		mav.addObject("title", "注册");
		return mav;
	}
	
	/**
	 * #注册会员 接口
	 * /member/add
	 */
	@ResponseBody
	@RequestMapping("/api/reg")
	public JSONObject api_reg( Member member,@RequestParam(value = "yzm", required = false) String yzm, 
			HttpSession session  )throws Exception {
		JSONObject result = new JSONObject();
		member.setCreateDateTime(new Date());
		member.setImageUrl("/image/base/default_head_img.jpg");
		BigDecimal balance = new BigDecimal(0);
		member.setBalance(balance);
		member.setIntegral(0);
		
		String sessionYZM = (String) session.getAttribute("yzm");
		if(sessionYZM.equals(yzm)) {
		}else {
			result.put("success", false);
			result.put("msg", "验证码不对");
			return result;
		}
		
		
		//判断 帐号不能存在
		List<Member> members = memberDao.findByName(member.getName());
		if(members.size()>0) {
			result.put("success", false);
			result.put("msg", "帐号已存在");
			return result;
		}
		//判断 帐号不能存在
		memberDao.save(member) ;
		result.put("success", true);
		result.put("msg", "注册成功");
		return result;
	}
	
	
	/**
	 * #用户主页
	 * /member/home  
	 */
	@RequestMapping("/home")
	public ModelAndView home(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Member member = (Member) session.getAttribute("member");
		
		
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			member = memberDao.findId(member.getId());
			mav.addObject("member", member);
			mav.setViewName("/pc/member/home.html");
			mav.addObject("title", "个人中心");
		}
		return mav;
	}
	
	/**	
	 *  #我的订单
	 *  /member/order
	 */
	@RequestMapping("/order")
	public ModelAndView member_order(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "我的订单");
		Member member =  (Member) session.getAttribute("member");
		member = memberDao.findId(member.getId());
		mav.addObject("member",member);
		mav.setViewName("/pc/order/my_order.html");
		return mav;
	}
	
	
	/**
	 * #用户 修改个人资料
	 * /member/modifyInfo  
	 */
	@RequestMapping("/modifyInfo")
	public ModelAndView modifyInfo(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Member member = (Member) session.getAttribute("member");
		
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			member = memberDao.findId(member.getId());
			mav.addObject("member", member);
			mav.setViewName("/pc/member/modifyInfo.html");
			mav.addObject("title", "修改个人资料");
		}
		return mav;
	}
	
	/**
	 * 
	 * /member/add_cropper_image
	 * imgData=  data:image/png;base64,iVBORw0KGgoAAAiVBORw0KGgoAAAiVBORw0KGgoAAA后面非常多的字符，就是imgData
	 * 
	 */
	@ResponseBody
	@RequestMapping("/add_cropper_image")
	public JSONObject add_cropper_image(@RequestParam(value="imgData",required=false)String imgData, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        
	        //取得根目录带d://dxxxxx/tomcat/ruzhou/
			String webPath=request.getServletContext().getRealPath("");
			//定义根目录下面的文件夹
			String  uploadFile = "/static/upload_image/member/"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
			String fileName = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+".jpg";
			
			//调用产生文件夹的方法
			FileUtil.makeDirs(webPath+uploadFile);
			
			imgData = imgData.replace("data:image/png;base64,", "");
			
			Base64Util.GenerateImage(imgData, webPath+uploadFile+fileName);
			 result.put("success", true);
	         result.put("path", uploadFile+fileName);
	        return result;
	}
	
	/**
	 * /member/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update( Member member  , HttpServletResponse response, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		memberService.update(member) ;
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}
	
	
	/**
	 * #用户 修改密码
	 * /member/modifyPWD  
	 */
	@RequestMapping("/modifyPWD")
	public ModelAndView modifyPWD(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Member member = (Member) session.getAttribute("member");
		
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			member = memberDao.findId(member.getId());
			mav.addObject("member", member);
			mav.setViewName("/pc/member/modifyPWD.html");
			mav.addObject("title", "修改密码");
		}
		return mav;
	}
	
	/**
	 * 注销
	 * /member/logout
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		session.setAttribute("member", null);
		return "redirect:/member/login";
	}
	
	
	/**
	 * #发送验证码
	 * /member/api/send/yzm
	 */
	@ResponseBody
	@RequestMapping("/api/send/yzm")
	public JSONObject api_member_send_yzm(@RequestParam(value = "phone", required = false) String phone,HttpSession session)throws Exception {
		
		String url = "https://sms.yunpian.com/v2/sms/single_send.json";
		String text = "【蚂蚁科技】您的验证码是CODE。如非本人操作，请忽略本短信";
		
		String yzm = MyUtil.createYZM(4);
		session.setAttribute("yzm", yzm);
		text = text.replaceAll("CODE", yzm);
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("apikey", "1542bac06a68f196e803000291ee2bfe");
	    params.put("text", text);
	    params.put("mobile", phone);
		String result = HttpClient.doPost(url , params);
		JSONObject obj = JSONObject.fromObject(result);
	    //{"http_status_code":400,"code":2,"msg":"请求参数格式错误","detail":"参数 mobile 格式不正确，mobile:12345678911","success":false}
	    Integer code = obj.getInt("code");
	    if(code==0) {
	    	obj.put("success", true);
	    }else {
	    	obj.put("success", false);
	    }
		return obj;
	}
	
	/**
	 * #积分兑换
	 * /member/integral/exchange
	 */
	@RequestMapping("/integral/exchange")
	public ModelAndView member_integral_exchange(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		Member member = (Member) session.getAttribute("member");
		if(member==null) {
			mav.setViewName("/pc/member/login.html");
			mav.addObject("title", "登录");
		}else {
			member = memberDao.findId(member.getId());
			mav.addObject("member", member);
			mav.setViewName("/pc/member/member_integral_exchange.html");
			mav.addObject("title", "积分兑换");
		}
		return mav;
	}
	
	
	/**
	 * #积分 税换 优惠券
	 * /member/api/exchange
	 */
	@ResponseBody
	@RequestMapping("/api/exchange")
	public JSONObject api_exchange(HttpSession session)throws Exception {
		JSONObject result = new  JSONObject();
		Member member = (Member) session.getAttribute("member");
		member = memberDao.findId(member.getId());
		if(member.getIntegral()>=100) {
			member.setIntegral(member.getIntegral()-100);
			memberDao.save(member);
			MyCoupon myCoupon = new MyCoupon();
			myCoupon.setMember(member);
			myCoupon.setCreateDateTime(new Date());
			BigDecimal bigDecimal = new BigDecimal(10);
			myCoupon.setJine(bigDecimal);
			myCoupon.setName("10元优惠券");
			myCoupon.setState(0);
			myCouponDao.save(myCoupon);
			
			result.put("success", true);
			result.put("msg", "兑换成功");
		}else {
			result.put("success", false);
			result.put("msg", "兑换失败，积分不足");
		}
		return result;
	}
	
	
}
