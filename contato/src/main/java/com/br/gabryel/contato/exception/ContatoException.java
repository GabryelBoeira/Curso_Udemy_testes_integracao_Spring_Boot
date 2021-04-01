package com.br.gabryel.contato.exception;

public class ContatoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContatoException(Exception e) throws ContatoException {
		super(e);
	}
}
