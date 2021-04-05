package com.br.contato.testRestTemplate.service;

import java.util.List;
import java.util.Optional;

import com.br.contato.testRestTemplate.model.Contato;

public interface ContatoService {
	
	List<Contato> buscarContatos();

	Optional<Contato> buscarContato(Long id);

	Contato inserirOuAlterar(Contato contato);

	void remover(Long id);
}
