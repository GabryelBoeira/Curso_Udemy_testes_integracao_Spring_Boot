package com.br.gabryel.contato.service;

import java.util.List;

import com.br.gabryel.contato.exception.ContatoException;
import com.br.gabryel.contato.model.Contato;

public interface ContatoService {

	void inserir(Contato contato) throws ContatoException;
	
	void remover(Long id);
	
	List<Contato> buscarTodosContatos();
	
	Contato buscarPorId(Long id);
	
}

