package com.br.gabryel.contato.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.br.gabryel.contato.model.Contato;

@SpringBootTest
public class ContatoRepositoryCustomIntegrationTest {

	@Autowired
	ContatoRepository contatoRepository;


	@BeforeEach
	public void setup() {
		
		Contato contato1 = new Contato("41", "123451234", "Chefe João");
		Contato contato2 = new Contato("41", "123451234", "Chefe");
		Contato contato3 = new Contato("41", "123451234", "Irmão do Chefe");
		Contato contato4 = new Contato("41", "123451234", "Amanda");
		
		List<Contato> lista = new ArrayList<Contato>() ; 
		lista.add(contato1);
		lista.add(contato2);
		lista.add(contato3);
		lista.add(contato4);
		
		contatoRepository.saveAll(lista);
	}

	@AfterEach 
	public void end() {
		contatoRepository.deleteAll();
	}
	
	@Test
	public void buscarPorNomeRetornaContato() {
		
		Contato resultado = contatoRepository.findFirstByNome("Chefe");
		
		assertTrue(resultado.getNome().equals("Chefe"));
	}
	
	@Test
	public void buscarTodosPorNomeIgonreCase() {
		
		List<Contato> resultado = contatoRepository.findAllByNomeIgnoreCaseContaining("Chefe");
		
		assertEquals(3, resultado.size());
	}
	
	@Test
	public void buscarTodosContatosPorOrdenacaoAscendente() {

		List<Contato> resultado = contatoRepository.buscarTodosContatosComOrdenacao(Sort.by(Sort.Direction.ASC, "nome"));
		
		assertTrue(resultado.get(0).getNome().equals("Amanda"));
	}
	
	@Test
	@Transactional
	public void buscarTodosContatosPorStreamOrdenado() {

		try (Stream<Contato> resultado = contatoRepository.buscarTodosContatos()) {
			assertTrue(resultado.findFirst().get().getNome().equals("Amanda"));
		}
		
	}
	 
}
