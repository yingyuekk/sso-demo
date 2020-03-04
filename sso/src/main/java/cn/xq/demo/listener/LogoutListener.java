package cn.xq.demo.listener;

import cn.xq.demo.common.RedisUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 功能描述
 *
 * @author xieqiong
 * @date 2020/3/4
 */
@Component
public class LogoutListener implements HttpSessionListener
{
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        RedisUtils redis = new RedisUtils();

    }
}
