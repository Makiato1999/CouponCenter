package com.couponcenter.coupon.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.couponcenter.coupon.customer.loadbalance.CanaryRuleConfiguration;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
//用于扫描Dao @Repository
@EnableJpaRepositories(basePackages = {"com.couponcenter"})
//用于扫描JPA实体类 @Entity，默认扫本包当下路径
@EntityScan(basePackages = {"com.couponcenter"})
@EnableDiscoveryClient
@LoadBalancerClient(value = "coupon-template-service", configuration = CanaryRuleConfiguration.class)
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
