package com.ketang.controller.admin.ser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ketang.dao.ser.PostDao;
import com.ketang.dao.ser.ReplyDao;
import com.ketang.entity.ser.Link;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.Post;
import com.ketang.service.ser.PostService;
import com.ketang.util.DateUtil;
import com.ketang.util.FileUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/post")
public class Admin_Post_Controller {
	
	@Resource
	private PostDao postDao ;
	@Resource
	private PostService postService ;
	/**
	 * /admin/post/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Post post ,BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			post.setClickHit(0);
			post.setReplyHit(0);
			post.setCreateDateTime(new Date());
			postDao.save(post);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	
	/**
	 * /admin/post/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(Post post)throws Exception {
		JSONObject result = new JSONObject();
		//linkService.update(link);
		result.put("success", true);
		result.put("msg", "修改成功");
		
		return result;
	}
	
	/**
	 * /admin/post/list
	 * @param page  默认1
	 * @param limit 数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "memberId", required = false) Integer memberId, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (memberId != null) {
			Member member = new Member();
			member.setId(memberId);
			map.put("member", member);
		}
		List<Post> list = postService.list(map, page - 1, limit);
		Long total = postService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/post/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			postDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
	
	
	
	/**
	 * /admin/post/add_file
	 */
	@ResponseBody
	@RequestMapping("/add_file")
	public JSONObject add_file(@RequestParam("file") MultipartFile file,HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        if(!file.isEmpty()){
	            String webPath=request.getServletContext().getRealPath("");
	            System.out.println(webPath);
	            String filePath= "/static/upload_file/venue_file/"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
	            //把文件名子换成（时间搓.png）
	            // String imageName="houtai_logo."+file.getOriginalFilename().split("\\.")[1];
	            System.out.println(file.getOriginalFilename().split("\\.")[0]);
	            System.out.println(file.getOriginalFilename().split("\\.")[1]);
	            //检测   文件夹有没有创建 
	            FileUtil.makeDirs(webPath+filePath);
	            String imageName=DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+file.getOriginalFilename().split("\\.")[0]+"."+file.getOriginalFilename().split("\\.")[1];
	            file.transferTo(new File(webPath+filePath+imageName));
	            result.put("code", 0);
	            result.put("msg", "上传成功");
	            JSONObject data = new JSONObject();
	            data.put("src", filePath+imageName);
	            data.put("title", "");
	            result.put("data", data);
	        }
	        return result;
	}
	
	
	
	
	
	
}
