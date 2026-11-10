package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SpringBootDemoApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> {
			System.out.println("Context loaded successfully");
		});
	}

}
