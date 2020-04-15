package com.ketang.controller.admin.ser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ketang.dao.ser.CouponDao;
import com.ketang.dao.ser.LunBoDao;
import com.ketang.entity.ser.Coupon;
import com.ketang.entity.ser.LunBo;
import com.ketang.service.ser.CouponService;
import com.ketang.service.ser.LunBoService;
import com.ketang.util.Base64Util;
import com.ketang.util.DateUtil;
import com.ketang.util.FileUtil;

import net.sf.json.JSONObject;



@Controller
@RequestMapping("/admin/coupon")
public class Admin_Coupon_Controller {
	

	@Resource
	private CouponDao couponDao  ;
	@Resource
	private  CouponService couponService  ;
	
	/**
	 * /admin/coupon/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Coupon coupon ,BindingResult bindingResult
			, HttpServletResponse response, HttpServletRequest request,HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			couponDao.save(coupon);
			result.put("success", true);
			result.put("msg", "添加成功");
			result.put("btn_disable", true);
			return result;
		}
	}
	
	
	/**
	 * /admin/coupon/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(Coupon coupon, HttpServletResponse response, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		couponService.update(coupon );
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}
	
	
	/**
	 * /admin/coupon/list
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Coupon> list = couponService.list(map, page-1, limit);
		Long total = couponService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/coupon/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request)
			throws Exception {
		String[] idsStr = ids.split(",");
		String webPath=request.getServletContext().getRealPath("");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			couponDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
	
	
}
