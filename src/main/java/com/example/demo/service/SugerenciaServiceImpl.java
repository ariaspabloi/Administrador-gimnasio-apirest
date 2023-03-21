package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Sugerencia;
import com.example.demo.repository.RepositorioSugerencia;

@Service
@Transactional
public class SugerenciaServiceImpl implements SugerenciaService{

	@Autowired
	RepositorioSugerencia repSugerencia;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	RecepcionistaService recepcionistaService;
	
	@Override
    public List<Sugerencia> buscarTodasLasSugerencias(){
    	return repSugerencia.findAll();
    }

    @Override
    public Optional<Sugerencia> buscarSugerenciaPorId(int id) {
    	return repSugerencia.findById(id);
    }

    @Override
    public boolean guardarSugerencia(Sugerencia sugerencia) {
    	if(sugerencia.getRecepcionista()!=null) {
    		if(!recepcionistaService.existById(sugerencia.getRecepcionista().getId_recepcionista())) return false;
    	}else if(sugerencia.getCliente()!=null) {
    		if(!clienteService.existById(sugerencia.getCliente().getId_cliente())) return false;
    		if(!clienteService.tieneContrato(sugerencia.getCliente().getId_cliente())) return false;
    	}else {
    		return false;
    	}
    	if(sugerencia.getDetalle().length() < 20) return false;
    	
    	sugerencia.setFecha(LocalDate.now());
    	repSugerencia.saveAndFlush(sugerencia);
        Optional<Sugerencia> sugerenciaOptional = repSugerencia.findById(sugerencia.getId_sugerencia());
        return sugerenciaOptional.isPresent();
    }

    @Override
    public boolean borrarSugerenciaPorId(int id) {
		Optional<Sugerencia> sugerenciaOptional = repSugerencia.findById(id);
    	if(sugerenciaOptional.isPresent()) {
    		repSugerencia.deleteById(id);
    		return true;
    	}else{
    		return false;
    	}
    }

	@Override
	public boolean actualizarSugerencia(Sugerencia sugerencia) {
		if(sugerencia.getRecepcionista()!=null) {
    		if(!recepcionistaService.existById(sugerencia.getRecepcionista().getId_recepcionista())) return false;
    	}else if(sugerencia.getCliente()!=null) {
    		if(!clienteService.existById(sugerencia.getCliente().getId_cliente())) return false;
    		if(!clienteService.tieneContrato(sugerencia.getCliente().getId_cliente())) return false;
    	}else {
    		return false;
    	}
    	if(sugerencia.getDetalle().length() < 20) return false;
		Optional<Sugerencia> sugerenciaOptional = repSugerencia.findById(sugerencia.getId_sugerencia());
		if(sugerenciaOptional.isPresent()) {
    		repSugerencia.saveAndFlush(sugerencia);
    		return true;
    	}else{
    		return false;
    	}
	}
	
	@Override
	public List<Sugerencia> reporteSugerencia() {
		// donde estado = 0 es pendiente y estado = 1 resuelta
		List<Sugerencia> reporte = new ArrayList<Sugerencia>();
		List<Sugerencia> reporteAux = repSugerencia.findAll();
		
		for(int i = 0; i < reporteAux.size(); i++) {
			if(reporteAux.get(i).getEstado() == 0) {
				reporte.add(reporteAux.get(i));
			}
		}
		return reporte;
	}
    

}
