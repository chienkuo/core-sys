package me.akuo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import me.akuo.domain.User;
import me.akuo.service.HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pc on 2017/4/7.
 */
@Transactional
@Service(version = "1.0.0", interfaceClass = HelloWorld.class, interfaceName = "me.akuo.service.HelloWorld", proxy = "javassist")
public class HelloWorldImpl implements HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldImpl.class);

    public User sayHello(String world) {
        User user = new User();
        user.setName("1111");
        user.setId("1111");
        if (logger.isDebugEnabled()) {
            logger.debug(user.toString());
        }
        return user;
    }
}
