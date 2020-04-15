package com.ketang.controller.admin.ser;

import java.math.BigDecimal;
import java.util.Date;
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

import com.ketang.dao.ser.LunBoDao;
import com.ketang.dao.ser.MemberDao;
import com.ketang.entity.ser.LunBo;
import com.ketang.entity.ser.Member;
import com.ketang.service.ser.LunBoService;
import com.ketang.service.ser.MemberService;
import com.ketang.util.Base64Util;
import com.ketang.util.DateUtil;
import com.ketang.util.FileUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/member")
public class Admin_Member_Controller {
	
	@Resource
	private MemberDao memberDao  ;
	@Resource
	private  MemberService memberService;
	
	/**
	 * /admin/member/update
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
	 * /admin/member/list
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
		
		List<Member> list = memberService.list(map, page-1, limit);
		Long total = memberService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	/**
	 * /admin/member/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request)
			throws Exception {
		String[] idsStr = ids.split(",");
		String webPath=request.getServletContext().getRealPath("");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			memberDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
	/**
	 * 
	 * /admin/member/add_cropper_image
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
	 * /admin/member/recharge
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/recharge")
	public JSONObject recharge( @RequestParam(value = "id", required = false) Integer id  ,@RequestParam(value = "jine", required = false) BigDecimal jine, HttpServletResponse response, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		Member member=	memberDao.findId(id)   ;
		member.setBalance(member.getBalance().add(jine));
		memberDao.save(member);
		result.put("success", true);
		result.put("msg", "充值成功");
		return result;
	}
	
	
	
}
