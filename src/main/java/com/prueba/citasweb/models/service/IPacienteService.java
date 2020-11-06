package com.prueba.citasweb.models.service;

import java.util.List;

import com.prueba.citasweb.models.entity.Paciente;

public interface IPacienteService {
	
	public List<Paciente> findAll();
	
	public Paciente findById(Long id);
	
	public Paciente save(Paciente paciente);
	
	public void delete(Long id); 

}
