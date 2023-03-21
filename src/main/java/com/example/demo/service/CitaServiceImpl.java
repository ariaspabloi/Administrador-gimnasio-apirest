package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cita;
import com.example.demo.model.Entrenador;
import com.example.demo.repository.RepositorioCita;

@Service
@Transactional
public class CitaServiceImpl implements CitaService{

	@Autowired
	RepositorioCita repCita;
	@Autowired
	EntrenadorService entrenadorService; 
	@Autowired
	ClienteService clienteService;
	
	@Override
	public List<Cita> buscarTodasLasCitas(){
		return repCita.findAll();
	}
	
	@Override
	public Optional<Cita> buscarCitaPorId(int id) {
		return repCita.findById(id);
	}

	@Override
	public boolean guardar(Cita cita) {
		if(!clienteService.existById(cita.getCliente().getId_cliente())) return false;
		if(!entrenadorService.buscarEntrenadorPorId(cita.getEntrenador().getId_entrenador()).isPresent()) return false;
		
		
		if(!clienteService.tieneContrato(cita.getCliente().getId_cliente())) return false;
		//Conseguir citas del entrenador
		Entrenador entrenador = entrenadorService.buscarEntrenadorPorId(cita.getEntrenador().getId_entrenador()).get();
		LocalDateTime inicio = cita.getFecha();
		LocalDateTime fin = inicio.plusMinutes(cita.getDuracion());
		LocalDateTime inicioAux,finAux;
		for(Cita c : entrenador.getCitas()){
			inicioAux= c.getFecha();
			finAux=inicioAux.plusMinutes(c.getDuracion());
			if((inicio.isAfter(inicioAux)) && (inicio.isBefore(finAux))
					|| (fin.isAfter(inicioAux) && fin.isBefore(finAux)) || inicio.equals(inicioAux)) return false;
		}
		Cita insertado = repCita.saveAndFlush(cita);
		if(insertado != null) {
			Optional<Cita> citaOptional = repCita.findById(insertado.getId_cita());
			return citaOptional.isPresent();
		}
		return false;
	}

	@Override
	public boolean actualizar(Cita cita) {
		Optional<Cita> citaOptional = repCita.findById(cita.getId_cita());
    	if(citaOptional.isPresent()) {
    		//Conseguir citas del entrenador
    		Entrenador entrenador = cita.getEntrenador();
    		LocalDateTime inicio = cita.getFecha();
    		LocalDateTime fin = inicio.plusMinutes(cita.getDuracion());
    		LocalDateTime inicioAux,finAux;
    		for(Cita c : entrenador.getCitas()){
    			inicioAux= c.getFecha();
    			finAux=inicioAux.plusMinutes(c.getDuracion());
    			if(inicio.isAfter(inicioAux) && inicio.isBefore(finAux)
    					|| fin.isAfter(inicioAux)) return false;
    		}
    		repCita.saveAndFlush(cita);
    		return true;
    	}else{
    		return false;
    	}
	}

	@Override
	public int borrarCitaPorId(int id) {
		Optional<Cita> citaOptional = repCita.findById(id);
		if(citaOptional.isPresent()) {
			LocalDateTime inicio = citaOptional.get().getFecha();
			LocalDateTime actual = LocalDateTime.now();
			//Si quedan menos de 24 horas, no se puede borrar
			if(inicio.isBefore(actual.plusDays(1))) {
				return 0;
			}
		
			//Si la fecha ya paso, no se puede borrar
			if(inicio.isBefore(actual)) {
				return 0;
			}
    			repCita.deleteById(id);
    			return 1;
		}else{
			return 2;
    	}
	}

}
