package com.ketang.util;

/**
 * @author ST.Rock
 * @date 2020/5/15 11:41
 * @desc
 */
public enum RedisKeyEnum {

    order("order","订单缓存")
    ,seckillList("seckillList","秒杀商品id及数量");

    private String key;

    private String msg;

    RedisKeyEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
