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
import com.prueba.citasweb.models.entity.Paciente;
import com.prueba.citasweb.models.service.IPacienteService;

@RestController
@RequestMapping("/api/rest/v1/pacientes")
public class PacienteController {
	
	@Autowired
	private IPacienteService pacienteService;
	
	
	@GetMapping("/listar")
	public List<Paciente> getPacientes() {
		return pacienteService.findAll();
	}
	
	@GetMapping("/ver/{id}")
	public ResponseEntity<Response> getPaciente(@PathVariable Long id){
		
		Response response = new Response();
		Paciente paciente = pacienteService.findById(id);
		
		if(paciente == null) {
			response.setMessage("No se encontró un paciente con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response.setMessage("Paciente encontrado");
		response.setOk(true);
		response.setResult(paciente);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Response> crearPaciente(@Valid @RequestBody Paciente paciente, BindingResult result){
		
		Response response = new Response();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream().map( field -> {
						return "Campo '" + field.getField() + "': " + field.getDefaultMessage();
					}).collect(Collectors.toList());
			
			response.setMessage("Errores en validación");
			response.setResults(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
		try {
			Paciente pacienteCreado = pacienteService.save(paciente);
			response = new Response(true, "Paciente creado con exito", pacienteCreado);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(Exception e) {
			response.setMessage("Ocurrio un error al crear el paciente");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<Response> actualizarPaciente(@Valid @RequestBody Paciente paciente, BindingResult result, @PathVariable Long id){
		
		Response response = new Response();
		
		Paciente pacienteOld = pacienteService.findById(id);
		
		if(pacienteOld == null) {
			response.setMessage("No se encontró un paciente con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream().map( field -> {
						return "Campo '" + field.getField() + "': " + field.getDefaultMessage();
					}).collect(Collectors.toList());
			
			response.setMessage("Errores en validacion");
			response.setResults(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
		try {
			paciente.setId(id);
			Paciente pacienteActualizado = pacienteService.save(paciente);
			response = new Response(true, "Paciente actualizado con exito", pacienteActualizado);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}catch(Exception e) {
			response.setMessage("Ocurrio un error al Actualizar el paciente");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Response> eliminarPaciente(@PathVariable Long id){
		
		Response response = new Response();
		Paciente pacienteOld = pacienteService.findById(id);
		
		if(pacienteOld == null) {
			response.setMessage("No existe un paciente con id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		try {
			pacienteService.delete(id);
			response.setMessage("Paciente eliminado con exito");
			response.setOk(true);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e) {
			response.setMessage("Error al intentar eliminar el paciente");
			response.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	
	
	

}
