package com.fjykt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fjykt.service.HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Reference(version = "1.0.0", check = false)
    private HelloWorld helloWorld;

    @RequestMapping(value = "/hello.json")
    @ResponseBody
    public String sayHello(@RequestParam String s) {
        logger.info(helloWorld.sayHello(s).toString());
        return helloWorld.sayHello(s).toString();
    }

    @RequestMapping(value = "webjarstest")
    public String webjars() {
        return "test/webjars";
    }
}
