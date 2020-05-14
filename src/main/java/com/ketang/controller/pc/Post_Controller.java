package com.ketang.controller.pc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ketang.dao.ser.PostDao;
import com.ketang.dao.ser.PostItemDao;
import com.ketang.entity.ser.Chapter;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Post;
import com.ketang.entity.ser.PostItem;
import com.ketang.entity.ser.Reply;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.PostItemService;
import com.ketang.service.ser.PostService;

@Controller
public class Post_Controller {
	
	@Resource
	private PostService postService ;
	@Resource
	private PostDao postDao   ;
	@Resource
	private PostItemDao postItemDao ;
	@Resource
	private PostItemService  postItemService ;
	
	
	/**
	 *  /post/index
	 */
	@RequestMapping("/post/index")
	public ModelAndView post_index(@RequestParam(value = "page", required = false) Integer page) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(page==null) {
			page =1;
		}
		Integer limit =  5 ;//第一页显示多少个贴 子
		Map<String, Object> map = new HashMap<String, Object>();
		List<Post> postList = postService.list(map , page-1, limit);
		Long total = postService.getTotal(map);
		
		mav.addObject("page", page);
		mav.addObject("limit", limit);
		mav.addObject("total", total);
		mav.addObject("postList", postList);
		mav.addObject("title", "论坛");
		mav.setViewName("/pc/post/index.html");
		return mav;
	}
	
	/**
	 *  /post/1
	 * @return springmvc会自动把这个id提出来 /post/1   会自动 提出1当id
	 * @throws Exception
	 */
	@RequestMapping("/post/{id}")
	public ModelAndView post_view(@PathVariable("id") Integer id,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		Post post      = postDao.findId(id);
		mav.addObject("post", post);
		mav.addObject("title", post.getTitle());
		
		//添加点击次数
		post.setClickHit(post.getClickHit()+1);
		postDao.save(post);
		//添加点击次数
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", post.getId());
		List<PostItem> postItemList = postItemService.list(map , 0,200) ;
		mav.addObject("postItemList", postItemList);
		
		
		mav.setViewName("/pc/post/view.html");
		return mav;
	}
	
	
	
	
	/**
	 *  /post/add
	 */
	@RequestMapping("/post/add")
	public ModelAndView post_add(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		Member member = (Member) session.getAttribute("member");
		mav.addObject("title", "发布贴子");
		mav.addObject("save_url", "/admin/post/add");
		mav.addObject("btn_text", "发布贴子");
		mav.setViewName("/pc/post/add.html");
		return mav;
	}
	
	
	/**
	 *  /member/post/manage
	 */
	@RequestMapping("/member/post/manage")
	public ModelAndView member_post_manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "我的发贴记录");
		mav.setViewName("/pc/post/member_post_manage.html");
		return mav;
	}
	
	
	/**
	 *  /post/search
	 */
	@RequestMapping("/post/search")
	public ModelAndView search(@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "page", required = false) Integer page) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "我的搜索");
		mav.addObject("q", q);
		
		if(page==null) {
			page =1;
		}
		Integer limit =  100 ;//第一页显示多少个贴 子
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("q", q);
		List<Post> postList = postService.list(map , page-1, limit);
		mav.addObject("postList", postList);
		
		mav.setViewName("/pc/post/search.html");
		return mav;
	}
	
	
	
	
}
