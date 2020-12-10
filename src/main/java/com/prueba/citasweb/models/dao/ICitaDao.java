package com.prueba.citasweb.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.prueba.citasweb.models.entity.Cita;

public interface ICitaDao extends CrudRepository<Cita, Long>{
	
	public List <Cita> findByFechaHora(Date fechaHora);
	
	@Query(value = "Select * from citas where fecha_hora like ?1 ", nativeQuery = true)
	public List <Cita> findCitasDia(String fecha);
	

}
