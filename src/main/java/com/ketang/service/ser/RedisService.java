package com.ketang.service.ser;

import redis.clients.jedis.Jedis;

/**
 * @author ST.Rock
 * @date 2020/5/7 18:47
 * @desc
 */
public interface RedisService {


    /**
     * 库存数量减一
     * */
    String incrBy(Integer id);

}
