package com.prueba.citasweb.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.citasweb.models.dto.Response;
import com.prueba.citasweb.models.entity.Medico;
import com.prueba.citasweb.models.service.IMedicoService;

@RestController
@RequestMapping("/api/rest/v1/medicos")
public class MedicoController {
	
	@Autowired
	private IMedicoService medicoService;
	
	@GetMapping("/listar")
	public List<Medico> getMedicos(){
		return medicoService.findAll();
	}
	
	@GetMapping("/ver/{id}")
	public ResponseEntity<Response> getMedico(@PathVariable Long id){
		
		Response response = new Response();
		Medico medico = medicoService.getById(id);
		
		if(medico == null){
			response.setMessage("No existe un médico con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response.setMessage("Médico encontrado");
		response.setOk(true);
		response.setResult(medico);
		return ResponseEntity.status(HttpStatus.OK).body(response);		
		
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Response> save(@Valid @RequestBody Medico medico, BindingResult result) {
		
		Response response = new Response();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream().map( field -> {
						return "El campo '" + field.getField() + "': " + field.getDefaultMessage();
					}).collect(Collectors.toList());
			
			response.setMessage("Errores en validación");
			response.setResults(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
		try {
			Medico medicoCreado = medicoService.save(medico);
			response = new Response(true, "El médico fue creado con éxito", medicoCreado);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(Exception e) {
			response.setMessage("Ocurripo un error al crear el médico!");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<Response> update(@Valid @RequestBody Medico medico, BindingResult result, @PathVariable Long id){
		
		Response response = new Response();
		
		Medico medicoOld = medicoService.getById(id);
		
		if(medicoOld == null){
			response.setMessage("No existe un médico con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
			
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream().map( field -> {
						return "El campo '" + field.getField() + "': " + field.getDefaultMessage();
					}).collect(Collectors.toList());
			
			response.setMessage("Errores en validación");
			response.setResults(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
		try{
			medico.setId(id);
			Medico medicoActualizado = medicoService.save(medico);
			response = new Response(true, "Médico actualizado con exito", medicoActualizado);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(Exception e){
			response.setMessage("Error al intentar actualizar el médico");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id){
		
		Response response = new Response();		
		Medico medicoOld = medicoService.getById(id);
		
		if(medicoOld == null){
			response.setMessage("No existe un médico con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		try {
			medicoService.delete(id);
			response.setMessage("Médico eliminado con exito");
			response.setOk(true);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}catch(Exception e) {			
			response.setMessage("Error al eliminar el médico");
			response.setError(e.getMessage());			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);			
		}
		
	}
		
	
	
	
	

}
