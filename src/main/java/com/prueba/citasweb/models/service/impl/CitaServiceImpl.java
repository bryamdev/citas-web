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
import com.prueba.citasweb.models.util.DateFormaterUtil;

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
	
	public void validarCita(Cita cita) {
				
		Medico medico = medicoService.getById(cita.getMedico().getId());
		Paciente paciente = pacienteService.findById(cita.getPaciente().getId());
		DateFormaterUtil format = new DateFormaterUtil();
		
		System.out.println("ID de la cita: " + cita.getId());
		
		if(medico == null) {
			throw new NotFoundException("No existe un médico con id: " + cita.getMedico().getId());
		}
		
		if(paciente == null) {
			throw new NotFoundException("No existe un paciente con id: " + cita.getPaciente().getId());
		}
		
		int horaCitaNueva = format.getHourOfDate(cita.getFechaHora());
		
		int horaMinimaAtencion = format.getHourOfDate(medico.getHoraInicioAtencion());
		int horaMaximaAtencion = format.getHourOfDate(medico.getHoraFinAtencion());
		
		System.out.println("Minima: " + medico.getHoraInicioAtencion());
		System.out.println("Maxima: " + medico.getHoraFinAtencion());
		System.out.println("Hora cita: " + horaCitaNueva);
		
		
		if(horaCitaNueva < horaMinimaAtencion || horaCitaNueva > (horaMaximaAtencion - 1)){
			System.out.println("El médico " + medico.getNombre() + " no atiende a esa hora!");
			throw new RuntimeException("El médico " + medico.getNombre() + " no atiende a esa hora!");
		}
		
		
		String fecha = format.dateToString(cita.getFechaHora()) + "%";
		System.out.println("Fecha a texto con util: " + fecha);
		List<Cita> citasDia = citaDao.findCitasDia(fecha);
		
		
		if(citasDia.size() > 0) {
			System.out.println("SIII SE ENCONTRARON CITAS CON EL METODO NUEVO");
			for(Cita c : citasDia) {
				System.out.println(c.getFechaHora());
			}
			
		}else {
			System.out.println("NOOO SE ENCONTRARON CITAS CON EL METODO NUEVO");
		}
		
		
		if(cita.getId() == null || cita.getId() == 0) {
			System.out.println("Es una cita nueva");
			
			for (Cita c : citasDia) {
				int hora = format.getHourOfDate(c.getFechaHora());			
				
				//Solo para nuevos
				if(cita.getMedico().getId() == c.getMedico().getId() && cita.getPaciente().getId() == c.getPaciente().getId()) {
					throw new RuntimeException("El medico y paciente ya tienen una cita para ese dia!");
				}
				
				if(cita.getMedico().getId() == c.getMedico().getId() && horaCitaNueva == hora) {
					throw new RuntimeException("El/la médico " + c.getMedico().getNombre() + " ya tiene una cita agendada a las " + hora +  " para ese dia");
				}
			}
			
		}else {
			System.out.println("Es una actualización");
			
			for (Cita c : citasDia) {
				int hora = format.getHourOfDate(c.getFechaHora());			
				
				//Solo para nuevos
				if(cita.getMedico().getId() == c.getMedico().getId() && cita.getPaciente().getId() == c.getPaciente().getId() && c.getId() != cita.getId()) {
					throw new RuntimeException("El medico y paciente ya tienen una cita para ese dia!");
				}
				
				if(cita.getMedico().getId() == c.getMedico().getId() && horaCitaNueva == hora && c.getId() != cita.getId()) {
					throw new RuntimeException("El/la médico " + c.getMedico().getNombre() + " ya tiene una cita agendada a las " + hora +  " para ese dia");
				}
			}
		}
		
				
	}

}
