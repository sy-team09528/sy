package com.ketang.controller.houtai.ser;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.HotWordDao;
import com.ketang.dao.ser.LinkDao;
import com.ketang.entity.ser.HotWord;
import com.ketang.entity.ser.Link;

@Controller
@RequestMapping("/houtai/hot/word")
public class HouTai_HotWord_Controller {
	
	@Resource
	private  HotWordDao hotWordDao  ;
	
	/**
	 * /houtai/hot/word/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/page/hot_word/hot_word_manage");
		return mav;
	}
	
	/**
	 * /houtai/hot/word/add
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/hot/word/add");
		mav.setViewName("/admin/page/hot_word/add_update");
		return mav;
	}
	
	
	/**
	 * /houtai/hot/word/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		HotWord hotWord = hotWordDao.findId(id);
		mav.addObject("hotWord", hotWord);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/hot/word/update?id=" + id);
		mav.setViewName("/admin/page/hot_word/add_update");
		return mav;
	}
	
	
	
}
