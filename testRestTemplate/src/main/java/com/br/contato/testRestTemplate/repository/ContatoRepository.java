package com.br.contato.testRestTemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.contato.testRestTemplate.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
