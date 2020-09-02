package com.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author aqiang9  2020-07-31
 */
@SpringBootApplication(scanBasePackages = "com.gulimall")
@EnableDiscoveryClient
public class MemberApplication {
    public static void main(String[] args){
        SpringApplication.run(MemberApplication.class,args) ;
    }
}
