package com.prueba.citasweb.models.service.impl;

import java.util.Calendar;
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
		
		Calendar citaNueva = Calendar.getInstance();
		citaNueva.setTime(cita.getFechaHora());
		
		int diaCitaNueva = citaNueva.get(Calendar.DAY_OF_MONTH);
		int mesCitaNueva = citaNueva.get(Calendar.MONTH);
		int anioCitaNueva = citaNueva.get(Calendar.YEAR);
		int horaCitaNueva = citaNueva.get(Calendar.HOUR);
		
		int horaMinimaAtencion = medico.getHoraInicioAtencion().getHours();
		int horaMaximaAtencion = medico.getHoraFinAtencion().getHours();
		
		System.out.println("Minima: " + medico.getHoraInicioAtencion());
		System.out.println("Maxima: " + medico.getHoraFinAtencion());
		
		
		if(citaNueva.get(Calendar.HOUR) < horaMinimaAtencion || citaNueva.get(Calendar.HOUR) > (horaMaximaAtencion - 1)){
			System.out.println("El médico " + medico.getNombre() + " no atiende a esa hora!");
		}
		
		List<Cita> citas = findAll();
		
		for(Cita c: citas) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(c.getFechaHora());
			
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH);
			int anio = cal.get(Calendar.YEAR);
			int hora = cal.get(Calendar.HOUR);
			
			System.out.println(dia + " - " + mes + " - " + anio);
						
			//fecha de la cita a guardar
			
			System.out.println("Nueva: " + citaNueva.get(Calendar.DAY_OF_MONTH) + " - " + citaNueva.get(Calendar.MONTH) + " - " + citaNueva.get(Calendar.YEAR));
			
			
			
			if(diaCitaNueva == dia && mesCitaNueva == mes && anioCitaNueva == anio) {
				System.out.println("Si hay cita ese dia, id: " + c.getId());
				
				if(cita.getMedico().getId() == c.getMedico().getId() && cita.getPaciente().getId() == c.getPaciente().getId()) {
					throw new RuntimeException("El medico y paciente ya tienen una cita para ese dia!");
				}
				
				if(cita.getMedico().getId() == c.getMedico().getId() && horaCitaNueva == hora) {
					throw new RuntimeException("El/la médico " + c.getMedico().getNombre() + " ya tiene una cita agendada a las " + hora +  " para ese dia");
				}
				
			}else {
				System.out.println("No hay citas ese dia");				
			}
			
		}
				
	}

}
