package com.entian.common.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.entian.common.service.mapper")
@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}


}
