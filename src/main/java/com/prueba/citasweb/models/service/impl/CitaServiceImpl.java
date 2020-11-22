package com.prueba.citasweb.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.citasweb.models.dao.ICitaDao;
import com.prueba.citasweb.models.entity.Cita;
import com.prueba.citasweb.models.entity.Medico;
import com.prueba.citasweb.models.entity.Paciente;
import com.prueba.citasweb.models.exceptions.NotFoundException;
import com.prueba.citasweb.models.service.ICitaService;
import com.prueba.citasweb.models.service.IMedicoService;
import com.prueba.citasweb.models.service.IPacienteService;

@Service
public class CitaServiceImpl implements ICitaService{
	
	@Autowired
	private ICitaDao citaDao;

	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Cita> findAll() {
		return (List<Cita>) citaDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Cita findById(Long id) {
		return citaDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Cita save(Cita cita){
		
		validarCita(cita);
		
		return citaDao.save(cita);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		citaDao.deleteById(id);
	}
	
	@SuppressWarnings("deprecation")
	public void validarCita(Cita cita) {
		
		Medico medico = medicoService.getById(cita.getMedico().getId());
		Paciente paciente = pacienteService.findById(cita.getPaciente().getId());
		
		if(medico == null) {
			throw new NotFoundException("No existe un médico con id: " + cita.getMedico().getId());
		}
		
		if(paciente == null) {
			throw new NotFoundException("No existe un paciente con id: " + cita.getPaciente().getId());
		}
		
		Integer horaMinimaAtencion = medico.getHoraInicioAtencion().getHours();
		Integer horaMaximaAtencion = medico.getHoraFinAtencion().getHours();
		
		System.out.println("Minima: " + medico.getHoraInicioAtencion());
		System.out.println("Maxima: " + medico.getHoraFinAtencion());
		
		
		if(cita.getFechaHora().getHours() < horaMinimaAtencion || cita.getFechaHora().getHours() > (horaMaximaAtencion - 1)){
			System.out.println("El médico " + medico.getNombre() + " no atiende a esa hora!");
		}
		

		
	}

}
