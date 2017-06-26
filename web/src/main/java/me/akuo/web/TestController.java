package me.akuo.web;

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
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/hi")
    @ResponseBody
    public String sayHello(@RequestParam String s) {
        if (logger.isInfoEnabled()) {
            logger.info(s);
        }
        System.out.println(s);
        return s;
    }

}
