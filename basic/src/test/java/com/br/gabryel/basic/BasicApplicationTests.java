package com.br.gabryel.basic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.gabryel.basic.service.BasicService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BasicService.class})
class BasicApplicationTests {

	@Autowired
	private BasicService basicService;
	
	
	@Test
	public void contextLoads() {
		basicService.helloWord();
		assertTrue(true);
	}

}
