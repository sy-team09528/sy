package com.ketang.service.ser;


import com.ketang.config.MyRabbitMQConfig;
import com.ketang.dao.ser.SeckillDao;
import com.ketang.entity.ser.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

/**
 * @author liaohong
 * @since 2019/8/20 10:31
 */
@Service
public class MQSeckillService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeckillDao seckillDao;

    /**
     * 监听库存消息队列，并消费
     *
     *
     */
    @RabbitListener(queues = MyRabbitMQConfig.STORY_QUEUE)
    public void decrByStock(Integer id) {
        LOGGER.info("库存消息队列收到的消息商品信息是：{}", id);
        /**
         * 调用数据库service给数据库对应商品库存减一
         */
        Seckill seckill = seckillDao.findByVenue_id(id);
        seckill.setNum(seckill.getNum()-1);
        seckillDao.save(seckill);
    }
}
