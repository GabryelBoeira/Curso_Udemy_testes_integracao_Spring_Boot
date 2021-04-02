package com.br.gabryel.contato.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.gabryel.contato.exception.ContatoException;
import com.br.gabryel.contato.model.Contato;
import com.br.gabryel.contato.repository.ContatoRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AgendaControllerIntegrationTest {

	@MockBean
	private ContatoRepository contatoRepository;

	@Autowired
	private AgendaController agendaController;

	private String nome = "Chefe";
	private String ddd = "41";
	private String telefone = "123451234";

	@Test
	public void inserirRegistrocomTelefoneNulException() throws ContatoException {

		when(contatoRepository.save((Contato) any()))
				.thenThrow(new ConstraintViolationException("O Telefone deve ser preenchido", null));

		assertThrows(ContatoException.class, () -> agendaController.inserirRegistro(new Contato(ddd, null, nome)));
	}

	@Test
	public void inserirComDddNullException() throws ContatoException {
		
		when(contatoRepository.save((Contato) any()))
				.thenThrow(new DataIntegrityViolationException("O DDD deve ser preenchido"));
		
		assertThrows(ContatoException.class, () -> agendaController.inserirRegistro(new Contato(null, telefone, nome)));
	}

	@Test
	public void inserirComNomeNullException() throws ContatoException {

		when(contatoRepository.save((Contato) any()))
				.thenThrow(new DataIntegrityViolationException("O Nome deve ser preenchido"));

		assertThrows(ContatoException.class, () -> agendaController.inserirRegistro(new Contato(ddd, telefone, null)));		
	}
	
	@Test
	public void removerRegistroDeveRemoverContato() {
		
		agendaController.removerRegistro(1L);
		
		verify(contatoRepository, times(1)).deleteById(1L);
	}
	
	@Test
	public void inserirRegistroDeveSalvarContato() throws ContatoException {
		
		Contato contato = new Contato(ddd, telefone, nome);
		
		agendaController.inserirRegistro(contato);
		
		verify(contatoRepository, times(1)).save(contato);
	}
}
