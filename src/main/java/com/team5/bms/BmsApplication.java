package com.team5.bms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BmsApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(BmsApplication.class);

	public static void main(String[] args) {
		logger.info("Hello BMS!");
		SpringApplication.run(BmsApplication.class, args);
		
	}

}
