package com.qf.shop_ssearch_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@DubboComponentScan("com.qf.serviceimpl")
@EnableTransactionManagement
public class ShopSsearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopSsearchServiceApplication.class, args);
	}

}

