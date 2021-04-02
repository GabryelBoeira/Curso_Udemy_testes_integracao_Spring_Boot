package com.br.gabryel.contato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.br.gabryel.contato.exception.ContatoException;
import com.br.gabryel.contato.model.Contato;
import com.br.gabryel.contato.service.ContatoService;

@Controller
public class AgendaController {
	
	@Autowired 
	private ContatoService contatoService;	
	
	public void inserirRegistro(Contato contato) throws ContatoException {	
		contatoService.inserir(contato);
	}
	
	public void removerRegistro(Long id) {
		
		contatoService.remover(id);
	}
	
	

}
