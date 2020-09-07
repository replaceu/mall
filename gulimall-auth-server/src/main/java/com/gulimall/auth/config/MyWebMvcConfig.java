package com.gulimall.auth.config;

/**
 * @author aqiang9  2020-09-01
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig{

    @Bean
    @Lazy
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/reg.html").setViewName("reg");
            }
        } ;
    }



}
