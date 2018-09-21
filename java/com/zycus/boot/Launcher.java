package com.zycus.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zycus.controller", "com.zycus.services", "com.zycus.filters"})
@EnableJpaRepositories(basePackages = "com.zycus.repository")
@EntityScan(basePackages = "com.zycus.entity")
@EnableFeignClients("com.zycus.proxy")
@EnableCaching
public class Launcher {

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}
}
