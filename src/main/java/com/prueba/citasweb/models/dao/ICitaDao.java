package com.prueba.citasweb.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.prueba.citasweb.models.entity.Cita;

public interface ICitaDao extends CrudRepository<Cita, Long>{

}
