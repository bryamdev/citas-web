package com.prueba.citasweb.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.citasweb.models.dao.IMedicoDao;
import com.prueba.citasweb.models.entity.Medico;
import com.prueba.citasweb.models.service.IMedicoService;

@Service
public class MedicoServiceImpl implements IMedicoService{
	
	@Autowired
	private IMedicoDao medicoDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Medico> findAll() {
		
		return (List<Medico>)medicoDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Medico getById(Long id) {
		return medicoDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Medico save(Medico medico) {
		return medicoDao.save(medico);
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		medicoDao.deleteById(id);
		
	}

}
