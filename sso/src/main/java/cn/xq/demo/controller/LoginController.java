package cn.xq.demo.controller;

import cn.xq.demo.common.CookieUtils;
import cn.xq.demo.common.E3Result;
import cn.xq.demo.common.RedisUtils;
import cn.xq.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 功能描述
 *
 * @author xieqiong
 * @date 2020/3/4
 */
@Controller
public class LoginController
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/")
    public String demo(){
        return "/login";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response){
        if (username != null && username.equals("lisi")
                && password != null && password.equals("123456")){
            // 3、如果正确生成token。
            String token = UUID.randomUUID().toString();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            RedisUtils redis = new RedisUtils();
            // 4、把用户信息写入redis，key：token value：用户信息
            redis.setStringRedisTemplate(stringRedisTemplate);
            redis.set("SESSION:" + token, user);
            // 5、设置Session的过期时间
            redis.expire("SESSION:" + token, 90);
            CookieUtils.setCookie(request, response, "token", token);
            String url = CookieUtils.getCookieValue(request, "url");
            if (url == null){
                return E3Result.ok(token);
            }
            return E3Result.ok(token,url);
        }
        return E3Result.build(400,"用户名密码错误");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        RedisUtils redis = new RedisUtils();
        redis.setStringRedisTemplate(stringRedisTemplate);
        redis.deleteAll();
        CookieUtils.deleteCookie(request,response,"token");
        CookieUtils.deleteCookie(request,response,"url");
        return "login";
    }
}
