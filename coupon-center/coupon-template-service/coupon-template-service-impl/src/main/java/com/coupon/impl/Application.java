package com.coupon.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
//@EnableJpaAuditing
//@ComponentScan(basePackages = {"com.coupon"})
@SpringBootApplication
@ComponentScan(basePackages = {"com.coupon"})
@EnableJpaRepositories(basePackages = {"com.coupon.dao"})
@EntityScan(basePackages = {"com.coupon.dao.entity"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}