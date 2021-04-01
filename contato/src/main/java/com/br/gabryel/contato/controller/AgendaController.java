package com.br.gabryel.contato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.br.gabryel.contato.exception.ContatoException;
import com.br.gabryel.contato.model.Contato;
import com.br.gabryel.contato.service.ContatoService;

@Controller
public class AgendaController {
	
	@Autowired 
	ContatoService contatoService;	
	
	private void inserirRegistro(String nome, String ddd, String telefone) throws ContatoException {
	
		Contato contato = new Contato(ddd, telefone, nome);
		contatoService.inserir(contato);
	}
	
	private void removerRegistro(Long id) {
		
		contatoService.remover(id);
	}
	
	

}
