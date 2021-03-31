package com.br.gabryel.contato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@Entity
public class Contato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O DDD deve ser preenchido")
	@Column(nullable = false)
	private String ddd;
	
	@NotBlank(message = "O Telefone deve ser preenchido")
	@Column(nullable = false)
	private String telefone;
	
	@NotBlank(message = "O nome deve ser preenchido")
	@Column(nullable = false)
	private String nome;
	
	public Contato() {}

	public Contato(String ddd, String telefone, String nome) {	
		this.ddd = ddd;
		this.telefone = telefone;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
