package com.fjykt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fjykt.domain.User;
import com.fjykt.mapper.HelloMapper;
import com.fjykt.service.HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pc on 2017/4/7.
 */
@Transactional
@Service(version = "1.0.0", interfaceClass = HelloWorld.class, interfaceName = "com.fjykt.service.HelloWorld", proxy = "javassist")
public class HelloWorldImpl implements HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldImpl.class);
    @Autowired
    private HelloMapper helloMapper;

    public User sayHello(String world) {
        logger.info(helloMapper.toString());
        return helloMapper.getUser("u1");
    }
}
