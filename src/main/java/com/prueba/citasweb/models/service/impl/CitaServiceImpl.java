package com.prueba.citasweb.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.citasweb.models.dao.ICitaDao;
import com.prueba.citasweb.models.entity.Cita;
import com.prueba.citasweb.models.service.ICitaService;

public class CitaServiceImpl implements ICitaService{
	
	@Autowired
	private ICitaDao citaDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Cita> findAll() {
		return (List<Cita>) citaDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Cita findById(Long id) {
		return citaDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Cita save(Cita cita) {
		return citaDao.save(cita);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		citaDao.deleteById(id);
	}

}
