package com.ketang.controller.pc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.ChapterDao;
import com.ketang.dao.ser.ChatDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Venue;

@Controller
public class Chapter_Controller {

	@Resource
	private ChapterDao chapterDao ;
	
	/**
	 *  /chat/1  这个id是venueid
	 * @return springmvc 会自动把这个id提出来 /a/shop/1    会自动 提出1当id
	 * @throws Exception
	 */
	@RequestMapping("/chapter/{id}")
	public ModelAndView chapter(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Chapter chapter   = chapterDao.findId(id);
		mav.addObject("chapter", chapter);
		mav.setViewName("/pc/chapter/view.html");
		return mav;
	}
	
	
}
