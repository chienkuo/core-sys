package com.fjykt.web;

import com.fjykt.service.HelloWorld;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by pc on 2017/4/7.
 */
@Controller
@RequestMapping(value = "/")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloWorld helloWorld;

    @RequestMapping(value = "/hello.json")
    @ResponseBody
    public String sayHello(@RequestParam String s) {
        if (logger.isInfoEnabled()) {
            logger.info(helloWorld.sayHello(s).toString());
        }
        System.out.println(s);
        return helloWorld.sayHello(s).toString();
    }

    @RequestMapping(value = "webjarstest")
    public String webjars() {
        return "test/webjars";
    }
}
