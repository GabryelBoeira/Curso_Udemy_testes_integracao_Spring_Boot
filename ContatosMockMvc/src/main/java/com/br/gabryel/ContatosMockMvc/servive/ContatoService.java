package com.br.gabryel.ContatosMockMvc.servive;

import java.util.List;

import com.br.gabryel.ContatosMockMvc.model.Contato;

public interface ContatoService {

	void inserir(Contato contato);
	
	void remover(Long id);
	
	List<Contato> buscarContatos();
	
	Contato buscarContato(Long id);
}
