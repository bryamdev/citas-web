package com.prueba.citasweb.models.service;

import java.util.List;

import com.prueba.citasweb.models.entity.Cita;
import com.prueba.citasweb.models.exceptions.NotFoundException;

public interface ICitaService {
	
	public List<Cita> findAll();
	
	public Cita findById(Long id);
	
	public Cita save(Cita cita) throws NotFoundException;
	
	public void delete(Long id);

}
