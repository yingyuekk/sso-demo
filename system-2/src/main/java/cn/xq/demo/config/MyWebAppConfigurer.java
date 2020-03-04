package cn.xq.demo.config;

import cn.xq.demo.interceptor.LoginInterceptor;
import cn.xq.demo.common.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能描述
 *
 * @author xieqiong
 * @date 2020/3/4
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        RedisUtils utils = new RedisUtils();
        utils.setStringRedisTemplate(stringRedisTemplate);
        registry.addInterceptor(new LoginInterceptor(utils)).addPathPatterns("/**");
    }
}
