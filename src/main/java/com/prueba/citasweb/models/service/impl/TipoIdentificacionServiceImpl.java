package com.prueba.citasweb.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.citasweb.models.dao.ITipoIdentificacionDao;
import com.prueba.citasweb.models.entity.TipoIdentificacion;
import com.prueba.citasweb.models.service.ITipoIdentificacionService;

@Service
public class TipoIdentificacionServiceImpl implements ITipoIdentificacionService{
	
	@Autowired
	private ITipoIdentificacionDao tipoIdentificacionService;
	
	@Transactional(readOnly = true)
	@Override
	public List<TipoIdentificacion> findAll() {
		
		return (List<TipoIdentificacion>) tipoIdentificacionService.findAll();
	}

}
