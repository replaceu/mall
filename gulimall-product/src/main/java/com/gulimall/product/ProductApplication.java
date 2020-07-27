package com.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author aqiang9  2020-07-27
 */
@SpringBootApplication
@MapperScan("com.gulimall.product.dao")
public class ProductApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductApplication.class,args) ;
    }
}
