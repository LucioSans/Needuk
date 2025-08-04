package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Importe esta classe

@SpringBootTest
@ActiveProfiles("test") // Adicione esta linha
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
