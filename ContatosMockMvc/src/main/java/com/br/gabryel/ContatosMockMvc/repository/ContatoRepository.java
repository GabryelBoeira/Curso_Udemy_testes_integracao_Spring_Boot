package com.br.gabryel.ContatosMockMvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.gabryel.ContatosMockMvc.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
