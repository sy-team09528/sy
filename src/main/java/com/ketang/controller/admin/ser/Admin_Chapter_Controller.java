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

import com.ketang.dao.ser.ChapterDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Link;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.ChapterService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/chapter")
public class Admin_Chapter_Controller {

	@Resource
	private ChapterService chapterService;
	@Resource
	private ChapterDao chapterDao;
	
	/**
	 * /admin/chapter/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Chapter chapter    ,BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			chapter.setCreateDateTime(new Date());
			chapterDao.save(chapter);
			result.put("success", true);
			result.put("btn_disable", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	
	
	/**
	 * /admin/chapter/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(Chapter chapter    )throws Exception {
		JSONObject result = new JSONObject();
		chapterService.update(chapter);
		result.put("success", true);
		result.put("msg", "修改成功");
		
		return result;
	}
	
	
	
	
	/**
	 * /admin/chapter/list
	 * 
	 * @param page  默认1
	 * @param limit 数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "venueId", required = false) Integer venueId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		if (venueId != null) {
			Venue venue = new Venue();
			venue.setId(venueId);
			map.put("venue", venue);
		}

		List<Chapter> list = chapterService.list(map, page - 1, limit);
		Long total = chapterService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}

	/**
	 * /admin/chapter/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			chapterDao.deleteById(Integer.parseInt(idsStr[i]));
		}

		result.put("success", true);
		return result;
	}
	
	
	/**
	 * /admin/chapter/findById
	 * #通过Id查找实体
	 * @param id
	 */
	@ResponseBody	
	@RequestMapping("/findById")
	public Chapter findById(@RequestParam(value="id")Integer id,HttpServletResponse response)throws Exception{
		Chapter chapter  =chapterDao.findId(id);
		return chapter;
	}
	
	
	
}
