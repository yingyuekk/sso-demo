package cn.xq.demo.interceptor;

import cn.xq.demo.common.CookieUtils;
import cn.xq.demo.pojo.User;
import cn.xq.demo.common.RedisUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述
 *
 * @author xieqiong
 * @date 2020/3/4
 */
public class LoginInterceptor implements HandlerInterceptor
{

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RedisUtils redis;

    public LoginInterceptor(RedisUtils utils)
    {
        this.redis =utils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 前处理，执行handler之前执行此方法。
        //返回true，放行	false：拦截
        //1.从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
        //2.如果没有token，未登录状态，直接放行
        if (token.isEmpty()) {
            CookieUtils.setCookie(request, response, "url", String.valueOf(request.getRequestURL()));
            response.sendRedirect("http://localhost:89/");
            return true;
        }
        //3.取到token，根据token取用户信息
        User user = JSON.parseObject(redis.get("SESSION:" + token), User.class);
        //取不到用户信息，登录已经过期，返回登录过期
        if (user ==null){
            CookieUtils.setCookie(request, response, "url", String.valueOf(request.getRequestURL()));
            response.sendRedirect("http://localhost:89/");
            return true;
        }
        //取到用户信息更新token的过期时间
        redis.expire("SESSION:" + token, 90);
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，返回ModeAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //完成处理，返回ModelAndView之后。
        //可以再此处理异常
    }
}
