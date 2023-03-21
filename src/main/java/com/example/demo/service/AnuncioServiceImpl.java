package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Anuncio;
import com.example.demo.repository.RepositorioAnuncio;

@Service
@Transactional
public class AnuncioServiceImpl implements AnuncioService{

	@Autowired
	RepositorioAnuncio repAnuncio;
	
	@Autowired
	RecepcionistaService recepcionistaService;
	
	@Override
	public List<Anuncio> buscarTodosLosAnuncios(){
		return repAnuncio.findAll();
	}
	
	@Override
	public Optional<Anuncio> buscarAnuncioPorId(int id) {
		return repAnuncio.findById(id);
	}

	@Override
    public boolean guardar(Anuncio anuncio) {
		if(recepcionistaService.buscarRecepcionistaPorId(anuncio.getRecepcionista().getId_recepcionista()).isPresent()) {
    		Optional<Anuncio> anuncioOptional = repAnuncio.findById(anuncio.getId_anuncio());
    		if(anuncioOptional.isPresent()) {
    			return false;
    		}else {
    			if(anuncio.getFecha() == null) {
    				anuncio.setFecha(LocalDate.now());
    			}else if(anuncio.getFecha().isBefore(LocalDate.now())){
    				 return false;
    			}
    			repAnuncio.saveAndFlush(anuncio);
    			return true;
    		}
    	}
    	return false; 
	}

	@Override
    public boolean borrarAnuncioPorId(int id) {
		Optional<Anuncio> anuncioOptional = repAnuncio.findById(id);
		if(anuncioOptional.isPresent()) {
			repAnuncio.deleteById(id);
			return true;
		}
		return false;
	}
	
	public boolean actualizarAnuncio(Anuncio anuncio) {
		if(recepcionistaService.buscarRecepcionistaPorId(anuncio.getRecepcionista().getId_recepcionista()).isPresent()) {
    		Optional<Anuncio> anuncioOptional = repAnuncio.findById(anuncio.getId_anuncio());
    		if(anuncioOptional.isPresent()) {
				anuncio.setFecha(anuncioOptional.get().getFecha());
    			repAnuncio.saveAndFlush(anuncio);
    			return true;
    		}
    	}
    	return false; 
	}
}

