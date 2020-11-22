package com.prueba.citasweb.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.prueba.citasweb.models.entity.Cita;

public interface ICitaDao extends CrudRepository<Cita, Long>{
	
	public List <Cita> findByFechaHora(Date fechaHora);
	

}
