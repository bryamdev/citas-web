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
import com.prueba.citasweb.models.entity.Cita;
import com.prueba.citasweb.models.exceptions.NotFoundException;
import com.prueba.citasweb.models.service.ICitaService;

@RestController
@RequestMapping("/api/rest/v1/citas")
public class CitaController {

	@Autowired
	private ICitaService citaService;
	
	@GetMapping("/listar")
	public List<Cita> getCitas(){
		return citaService.findAll();
	}
	
	@GetMapping("/ver/{id}")
	public ResponseEntity<Response> getCita(@PathVariable Long id){
		
		Response response = new Response();
		
		Cita cita = citaService.findById(id);
		
		if(cita == null) {
			response.setMessage("No existe una cita con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response.setMessage("Cita encontrada con exito!");
		response.setResult(cita);		
		return ResponseEntity.status(HttpStatus.OK).body(response);
				
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Response> crearCita(@Valid @RequestBody Cita cita, BindingResult result){
		
		Response response = new Response();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream().map( field -> {
						return "El campo '" + field.getField() + "': " + field.getDefaultMessage();
					}).collect(Collectors.toList());
			
			response.setMessage("Error en validación!");
			response.setResults(errors);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		try {
			
			Cita citaNew = citaService.save(cita);
			response = new Response(true, "La cita fue creada con exito", citaNew);	
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}catch(NotFoundException e) {			
			response.setMessage("Error al crear la cita");
			response.setError(e.getMessage());			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}catch(Exception e) {
			response.setMessage("Error al crear la cita");
			response.setError(e.getMessage());			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<Response> actualizarCita(@Valid @RequestBody Cita cita, BindingResult result, @PathVariable Long id){
		
		Response response = new Response();
		Cita citaOld = citaService.findById(id);
				
		if(citaOld == null) {
			response.setMessage("No existe una cita con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);			
		}
				
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream().map( field -> {
						return "El campo '" + field.getField() + "': " + field.getDefaultMessage();
					}).collect(Collectors.toList());
			
			response.setMessage("Error en validación!");
			response.setResults(errors);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		try {
			
			cita.setId(id);
			Cita citaActualizada = citaService.save(cita);
			response.setMessage("Cita actualizada con exito");
			response.setResult(citaActualizada);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}catch(NotFoundException e) {
			response.setMessage("Error al actualizar la cita");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}catch(Exception e) {
			response.setMessage("Error al actualizar la cita");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Response> eliminarCita(@PathVariable Long id){
		
		Response response = new Response();
		Cita cita = citaService.findById(id);
		
		if(cita == null) {
			response.setMessage("No existe una cita con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);	
		}
		
		try {
			citaService.delete(id);
			response.setMessage("Cita eliminada con éxito!");
			response.setOk(true);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}catch(Exception e) {
			response.setMessage("Error al intentar eliminar la cita");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
}
