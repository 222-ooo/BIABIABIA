package com.sanley.coronavirus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {

    //静态资源释放
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    // 1. addResourceHandler 参数可以有多个添加资源处理器
    // 2. addResourceLocations 参数可以是多个，可以混合使用 file: 和 classpath : 资源路径


    @Override
    public void addViewControllers(ViewControllerRegistry registry) { //重载addViewControllers，两个路径都跳转到indexpage中。
        registry.addViewController("/index").setViewName("/indexpage");
        registry.addViewController("/").setViewName("/indexpage");

    }

}
