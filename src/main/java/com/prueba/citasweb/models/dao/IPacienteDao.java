package com.prueba.citasweb.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.prueba.citasweb.models.entity.Paciente;

public interface IPacienteDao extends CrudRepository<Paciente, Long> {

}
