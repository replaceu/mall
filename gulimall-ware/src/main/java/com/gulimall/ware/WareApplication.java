package com.gulimall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author aqiang9  2020-08-08
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients("com.gulimall.ware.feign")
@MapperScan(basePackages = "com.gulimall.ware.dao")
public class WareApplication {
    public static void main(String[] args){
        SpringApplication.run(WareApplication.class,args) ;
    }
}
