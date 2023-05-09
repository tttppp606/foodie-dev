package com.imooc.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){
        // 1. 添加cors配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //添加允许跨域访问的前端地址
        corsConfiguration.addAllowedOrigin("http://localhost:8080");

        corsConfiguration.addAllowedOrigin("http://103.151.216.190:8080"); //web门户页面IP
        corsConfiguration.addAllowedOrigin("http://web1.lichuang.space:8080"); //web门户页真实
        corsConfiguration.addAllowedOrigin("http://center1.lichuang.space:8080"); //用户中心页真实

        corsConfiguration.addAllowedOrigin("http://103.151.216.190"); //web门户页面IP
        corsConfiguration.addAllowedOrigin("http://web1.lichuang.space"); //web门户页面真实
        corsConfiguration.addAllowedOrigin("http://center1.lichuang.space"); //用户中心页真实

        corsConfiguration.addAllowedOrigin("*"); //放行所有的网站

        // 设置是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);
        // 设置可以访问的方法。例如：put，get等
        corsConfiguration.addAllowedMethod("*");
        // 设置允许的header
        corsConfiguration.addAllowedHeader("*");

        //2、设置什么请求用corsFilter拦截,
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        //所有请求都用被拦截，并使用设定的corsConfiguration
        corsSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(corsSource);
    }

}
