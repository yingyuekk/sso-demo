package cn.xq.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 功能描述
 *
 * @author xieqiong
 * @date 2020/3/4
 */
@Controller
public class DemoController
{

    @GetMapping("/index")
    public String demo(){
        return "/index";
    }
}
