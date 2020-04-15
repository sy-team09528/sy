package com.ketang.controller.admin.ser;

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

import com.ketang.dao.ser.HotWordDao;
import com.ketang.dao.ser.LinkDao;
import com.ketang.entity.ser.HotWord;
import com.ketang.entity.ser.Link;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@Controller
@RequestMapping("/admin/hot/word")
public class Admin_HotWord_Controller {

	@Resource
	private HotWordDao hotWordDao  ;
	
	/**
	 * /admin/hot/word/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid HotWord hotWord,BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			hotWordDao.save(hotWord);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
		
	}
	
	/**
	 * /admin/hot/word/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(HotWord hotWord   )throws Exception {
		JSONObject result = new JSONObject();
		hotWordDao.save(hotWord);
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}
	
	/**
	 * /admin/hot/word/list
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
		
		Pageable pageable= PageRequest.of(page-1, limit, Sort.Direction.ASC,"sort");
		Page<HotWord> pageList = hotWordDao.findAll(pageable);
		List<HotWord> list = pageList.getContent();//拿到list集合
		long total = hotWordDao.count();
		
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	/**
	 * /admin/hot/word/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			hotWordDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		
		result.put("success", true);
		return result;
	}
	
	
}
