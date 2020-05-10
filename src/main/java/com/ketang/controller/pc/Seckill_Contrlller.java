package com.ketang.controller.pc;

import com.ketang.VO.SeckillVO;
import com.ketang.config.MyRabbitMQConfig;
import com.ketang.dao.ser.OrderDao;
import com.ketang.dao.ser.VenueDao;
import com.ketang.entity.base.User;
import com.ketang.entity.ser.Member;
import com.ketang.entity.ser.Order;
import com.ketang.entity.ser.Venue;
import com.ketang.service.ser.RedisService;
import com.ketang.service.ser.SeckillService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill")
public class Seckill_Contrlller {

    @Autowired
    SeckillService seckillService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private Order_Controller order_controller;
    @Autowired
    private VenueDao venueDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderDao orderDao;

    //设置秒杀redis缓存的key
    private final String key = "order";


    @RequestMapping("/index")
    public ModelAndView seckillIndex(HttpServletResponse res, HttpServletRequest req) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/pc/seckill/home.html");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state",1);
        List<SeckillVO> seckillVOs= seckillService.list(map , 0, 100);
        mav.addObject("seckillVOs", seckillVOs);
        mav.addObject("title", "秒杀");
        return mav;
    }
    @ResponseBody
    @RequestMapping("/seckillRun/{id}/{md5}")
    public JSONObject seckillRun(@PathVariable("id")Integer id,
                                 @PathVariable("md5") String md5,
                                 HttpServletResponse res, HttpServletRequest req, HttpSession session) throws Exception {
       Member member =(Member) session.getAttribute("member");
//        System.out.println("参加秒杀的用户是："+member.getId()+"，秒杀的商品是："+id);
        Order order = (Order) redisTemplate.boundHashOps(key).get(member.getId()+"-"+id);
        if (order == null) {
            //说明redis缓存中没有此key对应的value
            //查询数据库，并将数据放入缓存中
            order = orderDao.findByMemberIdAndVenueId(member.getId(),id);
            if (order == null) {
                //说明没有查询到
                return new Exposer(false, seckillId);
            } else {
                //查询到了，存入redis缓存中。 key:秒杀表的ID值； value:秒杀表数据
                redisTemplate.boundHashOps(key).put(seckill.getSeckillId(), seckill);
                logger.info("RedisTemplate -> 从数据库中读取并放入缓存中");
            }
        } else {
            logger.info("RedisTemplate -> 从缓存中读取");
        }
        JSONObject result = new JSONObject();
        //先从redis减库存，防止超卖
        String messge = redisService.incrBy(id);
        if (messge.equals("抢购成功")){
            //发消息给库存消息队列，将库存数据减一
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, id);
            Venue venue = venueDao.findId(id);
            result=order_controller.api_order_add(id,null,venue.getPrice(),session);
        }else {
            result.put("success", false);
            result.put("msg", messge);
        }
        return result;
    }
}
