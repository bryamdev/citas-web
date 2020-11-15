package com.prueba.citasweb.models.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String mensaje) {
		super(mensaje);
	}

}
