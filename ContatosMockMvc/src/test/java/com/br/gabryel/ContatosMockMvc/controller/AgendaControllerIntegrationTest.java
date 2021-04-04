package com.br.gabryel.ContatosMockMvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AgendaControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveMostrarTodosOsContatos() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
	}

}
