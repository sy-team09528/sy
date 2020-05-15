package com.ketang.service.ser;

import com.ketang.dao.ser.SeckillDao;
import com.ketang.util.RedisKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author ST.Rock
 * @date 2020/5/7 18:50
 * @desc
 */

@Service
public class RedisServiceImpl implements RedisService {
    Jedis jedis=new Jedis("localhost");
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillDao seckillDao;

    /**
     * Redis库存数量减一
     */
    @Override
    public String incrBy(Integer id) {
        String messge=null;
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(RedisKeyEnum.seckillList.getKey());
//        String key=id.toString();
        //redis 监视一个key，当这个key的值发生改变时候，事务提交失败
        redisTemplate.watch(boundHashOperations.get(id));
//        jedis.watch(key);
//        String string = jedis.get(key);
        //得到商品的数量
         Integer currentnum = (Integer) boundHashOperations.get(id);
        if (currentnum >= 1) {
            //进行秒杀环节
            //开启事务
            SessionCallback<Object> callback = new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    //库存数量减一
                    operations.boundHashOps(RedisKeyEnum.seckillList.getKey()).increment(id,-1);
                    return operations.exec();
                }
            };
            // [true, true, true]
            //System.out.println(redisTemplate.execute(callback));
            Object exec = redisTemplate.execute(callback);
            if (exec == null ) {
                messge="抢购失败";
            }else {
                messge="抢购成功";
            }
        }else {
            messge="商品已抢购完";
        }
        return messge;
    }
}
