package com.ketang.service.ser;

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

    /**
     * Redis库存数量减一
     */
    @Override
    public String incrBy(Integer id) {
        String messge=null;
        String key=id.toString();
        //redis 监视一个key，当这个key的值发生改变时候，事务提交失败
        jedis.watch(key);
        String string = jedis.get(key);
        //得到商品的数量
        int currentnum = Integer.parseInt(string);
        if (currentnum >= 1) {
            //进行秒杀环节
            //开启事务
            Transaction multi = jedis.multi();
            //让商品减少一个
            multi.incrBy(key,-1);
            //提交事务.如果事务提交失败，返回值为空
            List<Object> exec = multi.exec();
            if (exec == null || exec.size()==0) {
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
