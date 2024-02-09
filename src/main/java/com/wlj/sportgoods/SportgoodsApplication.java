package com.wlj.sportgoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan(basePackages = {"com.wlj.sportgoods.*.mapper"})
public class SportgoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportgoodsApplication.class, args);
	}

}
