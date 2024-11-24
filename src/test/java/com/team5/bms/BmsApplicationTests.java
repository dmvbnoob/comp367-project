package com.team5.bms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
class BmsApplicationTests {

	@Autowired
    private RestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

}
