package com.br.gabryel.contato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.gabryel.contato.exception.ContatoException;
import com.br.gabryel.contato.model.Contato;
import com.br.gabryel.contato.repository.ContatoRepository;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
public class ContatoServiceIntegrationTest {

	@Autowired
	ContatoRepository contatoRepository;

	@Autowired
	ContatoService contatoService;

	private Contato contato;

	@BeforeEach
	public void setuo() {
		contato = new Contato("41", "123451234", "Chefe");
	}

	@Test
	public void inserirComTelefoneNuloException() {
		
		this.contato.setTelefone(null);

		assertThrows(ContatoException.class, () -> contatoService.inserir(this.contato), "O Telefone deve ser preenchido");
	}

	@Test
	public void inserirComDddNullException() {

		this.contato.setDdd(null);

		assertThrows(ContatoException.class, () -> contatoService.inserir(this.contato), "O Telefone deve ser preenchido");
	}

	@Test
	public void inserirComNomeNullException() {

		this.contato.setNome(null);

		assertThrows(ContatoException.class, () -> contatoService.inserir(this.contato), "O Nome deve ser preenchido");
	}
	
	@Test
	public void salvarContato() throws ContatoException {

		contatoService.inserir(this.contato);
		List<Contato> resultado = contatoService.buscarTodosContatos();

		assertEquals(1, resultado.size());
		contatoService.remover(resultado.get(0).getId());
	}

	
	@Test
	public void removerContato() throws ContatoException, NoSuchElementException {

		contatoService.inserir(this.contato);
		List<Contato> resultado = contatoService.buscarTodosContatos();
		assertEquals(1, resultado.size());
		
		
		contatoService.remover(resultado.get(0).getId());
		assertThrows(NoSuchElementException.class, () -> contatoService.buscarPorId(resultado.get(0).getId()));
	}
}
