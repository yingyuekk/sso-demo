package cn.xq.demo.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述
 *
 * @author xieqiong
 * @date 2020/3/4
 */


public class RedisUtils
{
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, Object value){
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    public void expire(String key,int vaue){
        stringRedisTemplate.expire(key, vaue, TimeUnit.SECONDS);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteAll(){
        Set<String> keys = stringRedisTemplate.keys("*");
        stringRedisTemplate.delete(keys);
    }
}
