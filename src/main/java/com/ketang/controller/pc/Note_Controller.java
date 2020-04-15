package com.ketang.controller.pc;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ketang.dao.ser.CouponDao;
import com.ketang.dao.ser.NoteDao;
import com.ketang.entity.ser.LunBo;
import com.ketang.entity.ser.Note;


@Controller
public class Note_Controller {
	@Resource
	private NoteDao noteDao ;
	
	/**
	 * @throws Exception
	 */
	@RequestMapping("/member/note")
	public ModelAndView coupon_index() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "我的笔记");
		mav.setViewName("/pc/note/member_note.html");
		return mav;
	}
	
	/**
	 * /member/note/add
	 */
	@RequestMapping("/member/note/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("btn_text", "添加");
		mav.addObject("flag", false);
		mav.addObject("save_url", "/admin/note/add");
		mav.setViewName("/pc/note/add_update");
		return mav;
	}
	
	/**
	 * /member/note/edit?id=1
	 */
	@RequestMapping("/member/note/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Note note = noteDao.findId(id);
		mav.addObject("note", note);
		mav.addObject("flag", true);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/note/update?id=" + id);
		mav.setViewName("/pc/note/add_update");
		return mav;
	}
	
	
	/**
	 * /member/note/view?id=1
	 */
	@RequestMapping("/member/note/view")
	public ModelAndView view(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		Note note = noteDao.findId(id);
		mav.addObject("note", note);
		mav.setViewName("/pc/note/view.html");
		return mav;
	}
	
	
	
	
}
