package com.br.gabryel.ContatosMockMvc.controller;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.br.gabryel.ContatosMockMvc.model.Contato;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class AgendaControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestEntityManager testEntityManager;

	private Contato contato;

	@BeforeEach
	public void start() {

		contato = new Contato("41", "123451234", "Chefe");
		testEntityManager.persist(contato);
	}

	@AfterEach
	public void stop() {

		testEntityManager.getEntityManager().createQuery("DELETE FROM Contato").executeUpdate();
	}

	@Test
	public void deveMostrarTodosOsContatos() throws Exception {

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/agenda/"));
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
