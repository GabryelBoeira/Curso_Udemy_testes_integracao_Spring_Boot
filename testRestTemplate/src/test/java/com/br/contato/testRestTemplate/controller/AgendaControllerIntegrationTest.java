package com.br.contato.testRestTemplate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.contato.testRestTemplate.model.Contato;
import com.br.contato.testRestTemplate.repository.ContatoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgendaControllerIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	private String nome = "Chefe";

	private String ddd = "0y";

	private String telefone = "9xxxxxxx9";

	@BeforeEach
	public void start() {
		contato = new Contato(nome, ddd, telefone);
		contatoRepository.save(contato);
	}

	@AfterEach
	public void end() {
		contatoRepository.deleteAll();
	}

	@Test
	public void deveMostrarTodosContatos() {
		
		ResponseEntity<String> resposta = testRestTemplate.exchange("/agenda/",HttpMethod.GET,null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

}
