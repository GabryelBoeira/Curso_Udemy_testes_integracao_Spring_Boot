package com.br.gabryel.ContatosMockMvc.servive.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.gabryel.ContatosMockMvc.model.Contato;
import com.br.gabryel.ContatosMockMvc.repository.ContatoRepository;
import com.br.gabryel.ContatosMockMvc.servive.ContatoService;

@Service
public class ContaoServiceImpl implements ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;
	
	@Override
	public void inserir(Contato contato) {
		
		contatoRepository.save(contato);
	}

	@Override
	public void remover(Long id) {
		
		contatoRepository.deleteById(id);
	}

	@Override
	public List<Contato> buscarContatos() {
		
		return contatoRepository.findAll();
	}

	@Override
	public Contato buscarContato(Long id) {
		
		return contatoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
	}

}
