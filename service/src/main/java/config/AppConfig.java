package config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import me.akuo.service.HelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Akuo on 2017/6/5.
 */
@Configuration
@Import(DataSourceConfig.class)
public class AppConfig {
    public static final String APPLICATION_NAME = "service-provider";
    public static final String REGISTRY_ADDRESS = "zookeeper://127.0.0.1:2181";
    public static final String ANNOTATION_PACKAGE = "me.akuo.service.impl";
    private static final Logger logger = LoggerFactory
            .getLogger(AppConfig.class);

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(APPLICATION_NAME);
        //applicationConfig.setMonitor("com.alibaba.dubbo.monitor.MonitorService");
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

    // 服务提供者协议配置
    @Bean
    public ProtocolConfig protocol() {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setThreads(200);
        return protocol;
    }

  /*  @Bean
    public ServiceConfig<HelloWorld> helloWorldServiceConfig(RegistryConfig registryConfig, ApplicationConfig applicationConfig, ProtocolConfig protocolConfig) {
        ServiceConfig<HelloWorld> serviceConfig = new ServiceConfig<HelloWorld>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setLoadbalance("roundrobin");
        serviceConfig.setInterface(HelloWorld.class);
        serviceConfig.setId("helloWorld");
        serviceConfig.setVersion("1.0.0");   // 这行必须要有，否则，你要哈哈了

        return serviceConfig;
    }*/

}
