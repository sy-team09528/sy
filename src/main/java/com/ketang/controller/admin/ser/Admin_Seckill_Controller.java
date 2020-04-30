package com.ketang.controller.admin.ser;

import com.ketang.VO.SeckillVO;
import com.ketang.dao.ser.SeckillDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.ser.Seckill;
import com.ketang.entity.ser.Venue;
import com.ketang.entity.ser.VenueType;
import com.ketang.service.ser.SeckillService;
import com.ketang.service.ser.VenueService;
import com.ketang.util.Base64Util;
import com.ketang.util.DateUtil;
import com.ketang.util.FileUtil;
import com.ketang.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
	Jedis jedis = new Jedis("localhost");
	
	/**
	 * /admin/venue/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Seckill seckill ,BindingResult bindingResult
			, HttpServletResponse response, HttpServletRequest request,HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		System.out.println(seckill);
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			seckill.setCreate_date_time(new Date());

			seckill.setState(0);
			seckill=seckillDao.save(seckill);
			jedis.set(seckill.getId().toString(), seckill.getNum().toString());
	        jedis.close();
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
	public JSONObject update(Seckill seckill, HttpServletResponse response, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		String webPath=request.getServletContext().getRealPath("");
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
			seckillDao.deleteById(Integer.parseInt(idsStr[i]));
			jedis.del(idsStr[i].toString());
		}
		result.put("success", true);
		return result;
	}
	
	
	
	
	
	/**
	 * 
	 * /admin/venue/add_imageUrl
	 */
	@ResponseBody
	@RequestMapping("/add_imageUrl")
	public JSONObject add_imageUrl(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        if(!file.isEmpty()){
	            String webPath=request.getServletContext().getRealPath("");
	            System.out.println(webPath);
	            String filePath= "/static/upload_image/venue_cover/"+ DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
	            //把文件名子换成（时间搓.png）
	            // String imageName="houtai_logo."+file.getOriginalFilename().split("\\.")[1];
	            //检测   文件夹有没有创建 
	            FileUtil.makeDirs(webPath+filePath);
	            String imageName= DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+".jpg";
	            file.transferTo(new File(webPath+filePath+imageName));
	            result.put("success", true);
	            result.put("path", filePath+imageName);
	        }
	        
	        return result;
	}
	
	
	/**
	 * 
	 * /admin/venue/add_imageUrl2
	 */
	@ResponseBody
	@RequestMapping("/add_imageUrl2")
	public JSONObject add_imageUrl2(@RequestParam("file2") MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        if(!file.isEmpty()){
	            String webPath=request.getServletContext().getRealPath("");
	            System.out.println(webPath);
	            String filePath= "/static/upload_image/venue_cover/"+ DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
	            //把文件名子换成（时间搓.png）
	            // String imageName="houtai_logo."+file.getOriginalFilename().split("\\.")[1];
	            //检测   文件夹有没有创建 
	            FileUtil.makeDirs(webPath+filePath);
	            String imageName= DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+".jpg";
	            file.transferTo(new File(webPath+filePath+imageName));
	            result.put("success", true);
	            result.put("path", filePath+imageName);
	        }
	        
	        return result;
	}
	
	
	/**
	 * 
	 * /admin/venue/add_cropper_image
	 * imgData=  data:image/png;base64,iVBORw0KGgoAAAiVBORw0KGgoAAAiVBORw0KGgoAAA后面非常多的字符，就是imgData
	 */
	@ResponseBody
	@RequestMapping("/add_cropper_image")
	public JSONObject add_cropper_image(@RequestParam(value="imgData",required=false)String imgData, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        
	        //取得根目录带d://dxxxxx/tomcat/ruzhou/
			String webPath=request.getServletContext().getRealPath("");
			//定义根目录下面的文件夹
			String  uploadFile = "/static/upload_image/venue_cover/"+ DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
			String fileName = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+".jpg";
			
			//调用产生文件夹的方法
			FileUtil.makeDirs(webPath+uploadFile);
			
			imgData = imgData.replace("data:image/png;base64,", "");
			
			Base64Util.GenerateImage(imgData, webPath+uploadFile+fileName);
			result.put("success", true);
	        result.put("path", uploadFile+fileName);
	        return result;
	}
	
	/**
	 * /admin/venue/findById
	 * #通过Id查找实体
	 * @param id
	 */
	@ResponseBody	
	@RequestMapping("/findById")
	public Venue findById(@RequestParam(value="id")Integer id, HttpServletResponse response)throws Exception{
		Venue venue = venueDao.findId(id);
		return venue;
	}
	
	/**
	 * /admin/venue/add_file
	 */
	@ResponseBody
	@RequestMapping("/add_file")
	public JSONObject add_file(@RequestParam("file2") MultipartFile file,HttpServletResponse response, HttpServletRequest request) throws Exception {
	        JSONObject result = new JSONObject();
	        if(!file.isEmpty()){
	            String webPath=request.getServletContext().getRealPath("");
	            System.out.println(webPath);
	            String filePath= "/static/upload_file/venue_file/"+ DateUtil.formatDate(new Date(), "yyyyMMdd")+"/";
	            //把文件名子换成（时间搓.png）
	            // String imageName="houtai_logo."+file.getOriginalFilename().split("\\.")[1];
	            System.out.println(file.getOriginalFilename().split("\\.")[0]);
	            System.out.println(file.getOriginalFilename().split("\\.")[1]);
	            //检测   文件夹有没有创建 
	            FileUtil.makeDirs(webPath+filePath);
	            String imageName= DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+file.getOriginalFilename().split("\\.")[0]+"."+file.getOriginalFilename().split("\\.")[1];
	            file.transferTo(new File(webPath+filePath+imageName));
	            result.put("success", true);
	            result.put("path", filePath+imageName);
	        }
	        return result;
	}
	
	
	
}
