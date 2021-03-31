package com.br.gabryel.contato.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.gabryel.contato.model.Contato;

import net.bytebuddy.TypeCache.Sort;

public interface ContatoRepository extends JpaRepository<Contato, Long>{

	Contato findFirstByNome(String nome);
	
	List<Contato> findAllByNomeIgnoreCaseContaining(String nome);
	
	@Query(value = "SELECT c FROM Contato c")
	List<Contato> buscarTodosContatosComOrdenacao(Sort sort);
	
	@Query(value = "SELECT c FROM Contato c ORDER BY nome")
	Stream<Contato> buscarTodosContatos();

}
