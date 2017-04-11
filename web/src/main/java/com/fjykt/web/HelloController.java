package com.fjykt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fjykt.service.HelloWorld;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pc on 2017/4/7.
 */
@Controller
@RequestMapping(value = "/")
public class HelloController {
    @Reference(version = "1.0.0")
    private HelloWorld helloWorld;

    @RequestMapping(value = "/hello.json")
    @ResponseBody
    public String sayHello(@RequestParam String s){
        System.out.println(helloWorld.sayHello(s));
        return helloWorld.sayHello(s).toString();
    }
}
