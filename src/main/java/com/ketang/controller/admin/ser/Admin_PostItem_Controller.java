package com.ketang.controller.admin.ser;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ketang.dao.ser.PostDao;
import com.ketang.dao.ser.PostItemDao;
import com.ketang.entity.ser.Post;
import com.ketang.entity.ser.PostItem;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/post/item")
public class Admin_PostItem_Controller {
	
	@Resource
	private PostItemDao postItemDao ;
	@Resource
	private PostDao postDao ;
	
	/**
	 * /admin/post/item/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid PostItem  postItem ,BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			postItem.setCreateDateTime(new Date());
			postItemDao.save(postItem);
			
			Post post = postDao.findId(postItem.getPostId());
			post.setReplyHit(post.getReplyHit()+1);
			postDao.save(post);
			
			
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	
	
}
