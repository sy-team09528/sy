package com.ketang.controller.pc;

import com.ketang.VO.SeckillVO;
import com.ketang.service.ser.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill")
public class Seckill_Contrlller {

    @Autowired
    SeckillService seckillService;
    @RequestMapping("/index")
    public ModelAndView seckill(HttpServletResponse res, HttpServletRequest req) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/pc/seckill/home.html");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state",1);
        List<SeckillVO> seckillVOs= seckillService.list(map , 0, 100);
        mav.addObject("seckillVOs", seckillVOs);
        mav.addObject("title", "秒杀");
        return mav;
    }
}
