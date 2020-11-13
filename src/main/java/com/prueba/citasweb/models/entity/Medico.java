package com.prueba.citasweb.models.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "medicos")
public class Medico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String identificacion;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoIdent_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private TipoIdentificacion tipoIdentificacion;

	@NotEmpty
	private String numTarjetaProfesional;
	
	private Integer aniosExperiencia;
	
	@NotEmpty
	private String especialidad;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	@JsonFormat(pattern = "HH:mm:ss")
	private Date horaInicioAtencion;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	@JsonFormat(pattern = "HH:mm:ss")
	private Date horaFinAtencion;
	
	
	public Medico() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumTarjetaProfesional() {
		return numTarjetaProfesional;
	}

	public void setNumTarjetaProfesional(String numTarjetaProfesional) {
		this.numTarjetaProfesional = numTarjetaProfesional;
	}

	public Integer getAniosExperiencia() {
		return aniosExperiencia;
	}

	public void setAniosExperiencia(Integer aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Date getHoraInicioAtencion() {
		return horaInicioAtencion;
	}

	public void setHoraInicioAtencion(Date horaInicioAtencion) {
		this.horaInicioAtencion = horaInicioAtencion;
	}

	public Date getHoraFinAtencion() {
		return horaFinAtencion;
	}

	public void setHoraFinAtencion(Date horaFinAtencion) {
		this.horaFinAtencion = horaFinAtencion;
	}
	
	
	
	
	
	
}
