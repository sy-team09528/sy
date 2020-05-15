package com.ketang.controller.admin.ser;

import com.ketang.VO.SeckillVO;
import com.ketang.dao.ser.SeckillDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Seckill;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.ser.SeckillService;
import com.ketang.service.ser.VenueService;
import com.ketang.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀管理员管理管理
 *
 * */
@Controller
@RequestMapping("/admin/seckill")
public class Admin_Seckill_Controller {
	@Autowired
	private SeckillDao seckillDao;
	@Resource
	private VenueDao venueDao;
	@Resource
	private VenueService venueService ;
	@Resource
	private SeckillService seckillService ;
	@Autowired
	private RedisTemplate redisTemplate;

	
	/**
	 * /admin/venue/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Seckill seckill ,BindingResult bindingResult
			, HttpServletResponse response, HttpServletRequest request,HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			seckill.setCreate_date_time(new Date());

			seckill.setState(0);
			seckill=seckillDao.save(seckill);
			redisTemplate.boundHashOps(RedisKeyEnum.seckillList.getKey()).put(seckill.getVenue_id(), seckill.getNum());
//			jedis.set(seckill.getId().toString(), seckill.getNum().toString());
//	        jedis.close();
			result.put("success", true);
			result.put("msg", "添加成功");
			result.put("btn_disable", true);
			return result;
		}
	}
	
	/**
	 * /admin/venue/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(@Valid Seckill seckill, HttpServletResponse response, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		//String webPath=request.getServletContext().getRealPath("");

		seckillService.update(seckill);
		result.put("success", true);
		result.put("msg", "修改成功");
		return result;
	}
	
	
	/**
	 * /admin/venue/list 
	 *   layuitable
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "venueTypeId", required = false) Integer venueTypeId,
			@RequestParam(value = "top", required = false) Integer top,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Venue venue=new Venue();
//		if(top!=null)
//			map.put("top", top);
		if(state!=null)
			map.put("state", state);
		if(StringUtil.isNotEmpty(q))
			map.put("q", q);
		if(venueTypeId!=null) {
			VenueType venueType = new VenueType();
			venueType.setId(venueTypeId);
			map.put("venueType", venueType);
		}
		List<SeckillVO> list = seckillService.list(map, page-1, limit);
		Long total = seckillService.getTotal(map);
		System.out.println(list);
		map.clear();
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/seckill/delete
	 * 15     15,55,66
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request)
			throws Exception {
		String[] idsStr = ids.split(",");
		//String webPath=request.getServletContext().getRealPath("");
		JSONObject result = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			Seckill seckill = seckillDao.findId(Integer.parseInt(idsStr[i]));
			redisTemplate.boundHashOps(RedisKeyEnum.seckillList.getKey()).delete(seckill.getVenue_id());
			seckillDao.deleteById(Integer.parseInt(idsStr[i]));
//			jedis.del(idsStr[i].toString());

		}
		result.put("success", true);
		return result;
	}
}
