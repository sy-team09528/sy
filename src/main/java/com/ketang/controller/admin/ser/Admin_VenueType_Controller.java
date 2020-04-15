package com.ketang.controller.admin.ser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ketang.dao.ser.VenueTypeDao;
import com.ketang.entity.ser.Link;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.ser.VenueTypeService;
import com.ketang.util.Base64Util;
import com.ketang.util.DateUtil;
import com.ketang.util.FileUtil;

import net.sf.json.JSONObject;



@Controller
@RequestMapping("/admin/venue/type")
public class Admin_VenueType_Controller {
	
	@Resource
	private VenueTypeDao venueTypeDao  ;
	@Resource
	private VenueTypeService venueTypeService  ;
	@Autowired 
	private ServletContext servletContext;
	
	/**
	 *   /admin/venue/type/add
	 * 
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid VenueType venueType    ,BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			venueType.setCreateDateTime(new Date());
			venueTypeDao.save(venueType);
			result.put("success", true);
			result.put("msg", "添加成功");
			
			//刷新linkList缓存
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("useIt", 1);
			List<VenueType> typeList = venueTypeService.list(map , 0, 101);
			servletContext.setAttribute("typeList", typeList);
			//刷新linkList缓存\
			
			
			return result;
		}
	}
	
	/**
	 * /admin/venue/type/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update( VenueType venueType )throws Exception {
		JSONObject result = new JSONObject();
		venueType.setUpdateDateTime(new Date());
		venueTypeService.update(venueType);
		result.put("success", true);
		result.put("msg", "修改成功");
		
		
		//刷新linkList缓存
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("useIt", 1);
		List<VenueType> typeList = venueTypeService.list(map , 0, 101);
		servletContext.setAttribute("typeList", typeList);
		//刷新linkList缓存\
		
		
		return result;
	}
	
	
	/**
	 * /admin/venue/type/list
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
		List<VenueType> list = venueTypeService.list(map, page-1, limit);
		long total = venueTypeService.getTotal(map);
		
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	
	/**
	 * /admin/venue/type/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			venueTypeDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		

		//刷新linkList缓存
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("useIt", 1);
		List<VenueType> typeList = venueTypeService.list(map , 0, 101);
		servletContext.setAttribute("typeList", typeList);
		//刷新linkList缓存\
		
		
		result.put("success", true);
		return result;
	}
	
	
	
	/**
	 * 
	 * /admin/venue/type/add_imageUrl
	 */
	@ResponseBody
	@RequestMapping("/add_imageUrl")
	public JSONObject add_imageUrl(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        if(!file.isEmpty()){
	            String webPath=request.getServletContext().getRealPath("");
	            System.out.println(webPath);
	            String filePath= "/static/upload_image/venue_cover/"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
	            //把文件名子换成（时间搓.png）
	            // String imageName="houtai_logo."+file.getOriginalFilename().split("\\.")[1];
	            //检测   文件夹有没有创建 
	            FileUtil.makeDirs(webPath+filePath);
	            String imageName=DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+".jpg";
	            file.transferTo(new File(webPath+filePath+imageName));
	            result.put("success", true);
	            result.put("path", filePath+imageName);
	        }
	        
	        return result;
	}
	
	
	/**
	 * 
	 * /admin/venue/type/add_cropper_image
	 * imgData=  data:image/png;base64,iVBORw0KGgoAAAiVBORw0KGgoAAAiVBORw0KGgoAAA后面非常多的字符，就是imgData
	 */
	@ResponseBody
	@RequestMapping("/add_cropper_image")
	public JSONObject add_cropper_image(@RequestParam(value="imgData",required=false)String imgData, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        
	        //取得根目录带d://dxxxxx/tomcat/ruzhou/
			String webPath=request.getServletContext().getRealPath("");
			//定义根目录下面的文件夹
			String  uploadFile = "/static/upload_image/venue_cover/"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
			String fileName = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+".jpg";
			
			//调用产生文件夹的方法
			FileUtil.makeDirs(webPath+uploadFile);
			
			imgData = imgData.replace("data:image/png;base64,", "");
			
			Base64Util.GenerateImage(imgData, webPath+uploadFile+fileName);
			 result.put("success", true);
	         result.put("path", uploadFile+fileName);
	        return result;
	}
	
	
	
	
}
