package com.prueba.citasweb.models.service;

import java.util.List;

import com.prueba.citasweb.models.entity.Cita;

public interface ICitaService {
	
	public List<Cita> findAll();
	
	public Cita findById(Long id);
	
	public Cita save(Cita cita);
	
	public void delete(Long id);

}
