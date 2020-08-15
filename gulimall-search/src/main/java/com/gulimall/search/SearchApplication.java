package com.gulimall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author aqiang9  2020-08-12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class,args) ;
    }
}
