//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.dubbo.container.spring;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.container.Container;
import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAnnotationContainer implements Container {
    private static final Logger logger = LoggerFactory.getLogger(SpringAnnotationContainer.class);
    static AnnotationConfigApplicationContext context;

    public SpringAnnotationContainer() {
    }

    public static AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public void start() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.start();
    }

    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable var2) {
            logger.error(var2.getMessage(), var2);
        }

    }
}
