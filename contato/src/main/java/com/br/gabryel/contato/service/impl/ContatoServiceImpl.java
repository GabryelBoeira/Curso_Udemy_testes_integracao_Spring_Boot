package com.br.gabryel.contato.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.gabryel.contato.exception.ContatoException;
import com.br.gabryel.contato.model.Contato;
import com.br.gabryel.contato.repository.ContatoRepository;
import com.br.gabryel.contato.service.ContatoService;

@Service
public class ContatoServiceImpl implements ContatoService {

	@Autowired
	ContatoRepository contatoRepository;
	
	@Override
	public void inserir(Contato contato) throws ContatoException {

		try {			
			contatoRepository.save(contato);
		} catch (Exception e) {
			throw new ContatoException(e);
		}
	}

	@Override
	public void remover(Long id) {
		
		contatoRepository.deleteById(id);
	}

	@Override
	public List<Contato> buscarTodosContatos() {
		return contatoRepository.findAll();
	}

	@Override
	public Contato buscarPorId(Long id) {		
		return contatoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
	}

}
