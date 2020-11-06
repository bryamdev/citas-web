package com.prueba.citasweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.citasweb.models.entity.TipoIdentificacion;
import com.prueba.citasweb.models.service.ITipoIdentificacionService;

@RestController()
@RequestMapping("/api/rest/v1/tiposIdentificacion")
public class TipoIdentificacionController {
	
	@Autowired
	private ITipoIdentificacionService tiposIdentService;
	
	@GetMapping("/listar")
	public List<TipoIdentificacion> getTiposIdent(){
		return tiposIdentService.findAll();
	}
	
	
	
}
