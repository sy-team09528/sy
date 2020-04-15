package com.ketang.controller.admin.ser;

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

import com.ketang.dao.ser.MemberDao;
import com.ketang.dao.ser.NoteDao;
import com.ketang.entity.ser.Coupon;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Note;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.NoteService;
import com.ketang.service.ser.OrderService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/note")
public class Admin_Note_Controller {
	
	@Resource
	private NoteDao noteDao  ;
	@Resource
	private NoteService  noteService  ;
	
	/**
	 * /admin/note/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Note note   ,BindingResult bindingResult
			, HttpServletResponse response, HttpServletRequest request,HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			note.setCreateDateTime(new Date());
			noteDao.save(note);
			result.put("success", true);
			result.put("msg", "添加成功");
			result.put("btn_disable", true);
			return result;
		}
	}
	
	
	/**
	 * /admin/note/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(Note note  , HttpServletResponse response, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		noteService.update(note );
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}
	
	
	
	/**
	 * /admin/note/list
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
		List<Note> list = noteService.list(map, page - 1, limit);
		Long total = noteService.getTotal(map);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	/**
	 * /admin/note/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request)
			throws Exception {
		String[] idsStr = ids.split(",");
		String webPath=request.getServletContext().getRealPath("");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			noteDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
	
	/**
	 * #通过Id查找实体
	 * @param id
	 */
	@ResponseBody	
	@RequestMapping("/findById")
	public Note findById(@RequestParam(value="id")Integer id,HttpServletResponse response)throws Exception{
		Note note  =noteDao.findId(id);
		return note;
	}
	
}
