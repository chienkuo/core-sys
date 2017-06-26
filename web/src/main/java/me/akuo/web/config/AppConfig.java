package me.akuo.web.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import me.akuo.service.HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Created by Akuo on 2017/6/5.
 */
@Configuration
@ComponentScan(basePackages = "me.akuo",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Reference.class})},
        excludeFilters =
                {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, ControllerAdvice.class})})
public class AppConfig {
    public static final String APPLICATION_NAME = "service-consumer";
    public static final String REGISTRY_ADDRESS = "zookeeper://127.0.0.1:2181";
    public static final String ANNOTATION_PACKAGE = "com.fjykt.web";
    private static final Logger logger = LoggerFactory
            .getLogger(AppConfig.class);

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(APPLICATION_NAME);
        applicationConfig.setMonitor("com.alibaba.dubbo.monitor.MonitorService");
        applicationConfig.setOwner("akuo");
        return applicationConfig;
    }

    @Bean
    public MonitorConfig monitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(REGISTRY_ADDRESS);
        registryConfig.setProtocol("zookeeper");
        return registryConfig;
    }

    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(ANNOTATION_PACKAGE);
        return annotationBean;
    }

    @Bean(name = "helloWorldService")
    public ReferenceConfig<HelloWorld> reference(ApplicationConfig applicationConfig, RegistryConfig registryConfig) {
        ReferenceConfig<HelloWorld> reference = new ReferenceConfig<HelloWorld>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setProtocol("dubbo");
        reference.setInterface(HelloWorld.class);
        reference.setId("helloWorld");
        reference.setVersion("1.0.0");   // 这行必须要有，否则，你要哈哈了
        return reference;
    }

    @Bean
    public HelloWorld demoService(ReferenceConfig<HelloWorld> referenceConfig){
        return referenceConfig.get();
    }


}
