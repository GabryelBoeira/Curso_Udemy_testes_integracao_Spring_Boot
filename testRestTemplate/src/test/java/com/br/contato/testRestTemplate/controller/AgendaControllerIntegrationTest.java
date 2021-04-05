package com.br.contato.testRestTemplate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

		ResponseEntity<String> resposta = testRestTemplate.exchange("/agenda/", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	/*** Utilizando o métodos testRestTemplate.exchange Forma Generica ***/

	// Forma Generica de requição para tipo GET
	@Test
	public void deveMostrarTodosContatosUsandoStringExchange() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/agenda/", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));

		String result = "[{\"id\":" + contato.getId() + ",\"ddd\":\"0y\","
				+ "\"telefone\":\"9xxxxxxx9\",\"nome\":\"Chefe\"}]";
		assertEquals(result, resposta.getBody());
	}

	@Test
	public void deveMostrarTodosContatosUsandoListExchange() {
		ParameterizedTypeReference<List<Contato>> tipoRetorno = new ParameterizedTypeReference<List<Contato>>() {
		};

		ResponseEntity<List<Contato>> resposta = testRestTemplate.exchange("/agenda/", HttpMethod.GET, null,
				tipoRetorno);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		assertEquals(1, resposta.getBody().size());
		assertEquals(contato, resposta.getBody().get(0));
	}

	@Test
	public void deveMostrarUmContatoExchange() {
		ResponseEntity<Contato> resposta = testRestTemplate.exchange("/agenda/contato/{id}", HttpMethod.GET, null,
				Contato.class, contato.getId());

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		assertEquals(contato, resposta.getBody());
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradoExchange() {

		ResponseEntity<Contato> resposta = testRestTemplate.exchange("/agenda/contato/{id}", HttpMethod.GET, null,
				Contato.class, 100);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	// Forma Generica de requição para tipo POST
	@Test
	public void salvarContatoDeveRetornarMensagemDeErro() {
		Contato contato = new Contato(nome, null, null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<List<String>> resposta = testRestTemplate.exchange("/agenda/inserir", HttpMethod.POST,
				httpEntity, new ParameterizedTypeReference<List<String>>() {
				});

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void inserirDeveSalvarContato() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta = testRestTemplate.exchange("/agenda/inserir", HttpMethod.POST, httpEntity,
				Contato.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

		Contato resultado = resposta.getBody();

		assertNotNull(resultado.getId());
		assertEquals(contato.getNome(), resultado.getNome());
		assertEquals(contato.getDdd(), resultado.getDdd());
		assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	/*** Utilizando métodos mais específicos para Requisições do tipo GET ***/

	@Test
	public void deveMostrarUmContatoComGetForEntity() {
		ResponseEntity<Contato> resposta = testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,
				contato.getId());

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		assertEquals(contato, resposta.getBody());
	}

	@Test
	public void deveMostrarUmContatoComGetForObject() {
		Contato resposta = testRestTemplate.getForObject("/agenda/contato/{id}", Contato.class, contato.getId());
		assertEquals(contato, resposta);
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradoComGetForEntity() {
		ResponseEntity<Contato> resposta = testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class, 100);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradogetForObject() {
		Contato resposta = testRestTemplate.getForObject("/agenda/contato/{id}", Contato.class, 100);
		assertNull(resposta);
	}

	/*** Utilizando métodos mais específicos para Requisições do tipo POST ***/

	@Test
	public void inserirDeveSalvarContatoComPostForEntity() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta = testRestTemplate.postForEntity("/agenda/inserir", httpEntity, Contato.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		Contato resultado = resposta.getBody();
		assertNotNull(resultado.getId());
		assertEquals(contato.getNome(), resultado.getNome());
		assertEquals(contato.getDdd(), resultado.getDdd());
		assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	@Test
	public void inserirContatoDeveSalvarContatoPostForObject() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		Contato resposta = testRestTemplate.postForObject("/agenda/inserir", httpEntity, Contato.class);

		assertNotNull(resposta.getId());
		assertEquals(contato.getNome(), resposta.getNome());
		assertEquals(contato.getDdd(), resposta.getDdd());
		assertEquals(contato.getTelefone(), resposta.getTelefone());
		contatoRepository.deleteAll();
	}

}
