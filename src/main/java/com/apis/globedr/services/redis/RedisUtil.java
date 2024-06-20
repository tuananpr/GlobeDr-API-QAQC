package com.apis.globedr.services.redis;

import com.apis.globedr.services.config.RedisCfg;
import com.rest.core.redis.AbsRedis;
import com.rest.core.redis.RedisFactory;


public class RedisUtil {
    private static AbsRedis instant = null;

    public static AbsRedis getInstant(){
        if(instant == null){
            RedisCfg redisCfg = new RedisCfg();
            instant = RedisFactory.init(
                    redisCfg.get("redis.host"),
                    redisCfg.getInt("redis.port"),
                    redisCfg.get("redis.password"),
                    Boolean.parseBoolean(redisCfg.get("redis.ssl")),
                    redisCfg.getInt("redis.retry"));

        }
        return instant;
    }

}
