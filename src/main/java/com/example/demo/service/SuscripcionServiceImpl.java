package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Suscripcion;
import com.example.demo.repository.RepositorioSuscripcion;

@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService{

	@Autowired
	RepositorioSuscripcion repSuscripcion;
	
	@Override
    public List<Suscripcion> buscarTodasLasSuscripciones(){
    	return repSuscripcion.findAll();
    }

    @Override
    public Optional<Suscripcion> buscarSuscripcionPorId(int id) {
    	return repSuscripcion.findById(id);
    }

    @Override
    public boolean guardar(Suscripcion suscripcion) {
    	Optional<Suscripcion> suscripcionOptional = repSuscripcion.findSuscripcionByNombre(suscripcion.getNombre());
    	if(suscripcionOptional.isPresent()) {
    		return false;
    	}
    	repSuscripcion.saveAndFlush(suscripcion);
    	return true;
    }

    @Override
    public boolean borrarSuscripcionPorId(int id) {
    	Optional<Suscripcion> suscripcionOptional = repSuscripcion.findById(id);
    	if(suscripcionOptional.isPresent()) {
    		repSuscripcion.deleteById(id);;
        	return true;
    	}
    	return false;
    }
    
    @Override
    public boolean existById(int id) {
    	return repSuscripcion.existsById(id);
    }
    
    @Override
    public boolean actualizarSuscripcion(Suscripcion suscripcion) {
    	Optional<Suscripcion> suscripcionOptional = repSuscripcion.findById(suscripcion.getId_suscripcion());
    	if(suscripcionOptional.isPresent()) {
    		repSuscripcion.saveAndFlush(suscripcion);
        	return true;
    	}
    	return false;
    }
}
