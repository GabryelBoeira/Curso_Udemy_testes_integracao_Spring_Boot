package com.br.gabryel.basic.profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.br.gabryel.basic.config.EnvironmentConfig;

@SpringBootTest
@ActiveProfiles("test")
public class ProfileIntegrationTest {
	
	@Autowired
	private EnvironmentConfig environmentConfig;
	
	@Test
	public void test() {
		environmentConfig.someMethod();
	}

}
