package com.prueba.citasweb.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.prueba.citasweb.models.entity.Medico;

public interface IMedicoDao extends CrudRepository<Medico, Long>{

}
