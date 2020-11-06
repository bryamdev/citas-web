package com.prueba.citasweb.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.citasweb.models.dao.IPacienteDao;
import com.prueba.citasweb.models.entity.Paciente;
import com.prueba.citasweb.models.service.IPacienteService;

@Service
public class PacienteServiceImpl implements IPacienteService{

	@Autowired
	private IPacienteDao pacienteDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Paciente> findAll() {
		return (List<Paciente>)pacienteDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Paciente findById(Long id) {
		return pacienteDao.findById(id).orElse(null);
	}
	
	@Transactional
	@Override
	public Paciente save(Paciente paciente) {
		return pacienteDao.save(paciente);
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		pacienteDao.deleteById(id);
	}

}
