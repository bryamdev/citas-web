package com.prueba.citasweb.models.service;

import java.util.List;

import com.prueba.citasweb.models.entity.Medico;

public interface IMedicoService {
	
	public List<Medico> findAll();
	
	public Medico getById(Long id);
	
	public Medico save(Medico medico);
	
	public void delete(Long id);

}
