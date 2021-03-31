package com.br.gabryel.contato.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.gabryel.contato.model.Contato;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
public class ContatoRepositoryIntegrationTest {

	@Autowired
	ContatoRepository contatoRepository;

	private Contato contato;

	@BeforeEach
	public void setup() {
		this.contato = new Contato("41", "123451234", "João");
	}

	@Test
	public void salvarComTelefoneNullException() {

		this.contato.setTelefone(null);

		assertThrows(DataIntegrityViolationException.class, () -> contatoRepository.save(this.contato));
	}

	@Test
	public void salvarComDddNullException() {

		this.contato.setDdd(null);

		assertThrows(DataIntegrityViolationException.class, () -> contatoRepository.save(this.contato));
	}

	@Test
	public void salvarComNomeNullException() {

		this.contato.setNome(null);

		assertThrows(DataIntegrityViolationException.class, () -> contatoRepository.save(this.contato));
	}

	@Test
	public void salvarContato() {

		contatoRepository.save(this.contato);
		List<Contato> resultado = contatoRepository.findAll();

		assertEquals(1, resultado.size());
	}

	@Test
	public void removerContato() {

		Contato resultado = contatoRepository.save(this.contato);
		contatoRepository.deleteById(resultado.getId());

		assertThrows(NoSuchElementException.class, () -> contatoRepository.findById(resultado.getId()).get());
	}

}
