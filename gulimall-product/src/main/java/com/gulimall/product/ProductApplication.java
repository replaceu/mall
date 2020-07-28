package com.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author aqiang9  2020-07-27
 */
@SpringBootApplication
@MapperScan("com.gulimall.product.dao")
@ComponentScan("com.gulimall")  // 将全局异常处理 扫描进入
public class ProductApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductApplication.class,args) ;
    }
}
