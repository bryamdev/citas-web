package com.prueba.citasweb.models.entity;

import java.util.Calendar;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "citas")
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Bogota")
	private Calendar fechaHora;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medico_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Medico medico;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Paciente paciente;
	
	@NotEmpty
	private String descripcion;

	public Cita() {
		
	}
	
	@PostConstruct
	private void postConstruct() {
		//setCalendarTimeZone();
	}
	
	@PrePersist
	public void prePersist() {
		//setCalendarTimeZone();
		TimeZone tz = TimeZone.getDefault();
		this.fechaHora.setTimeZone(tz);
	}
	
	private void setCalendarTimeZone() {
		TimeZone tz = TimeZone.getTimeZone("America/Bogota");
		this.fechaHora.setTimeZone(tz);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Calendar fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	

}
