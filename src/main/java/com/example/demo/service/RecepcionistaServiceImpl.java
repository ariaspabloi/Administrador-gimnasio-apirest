package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Recepcionista;
import com.example.demo.repository.RepositorioRecepcionista;

@Service
@Transactional
public class RecepcionistaServiceImpl implements RecepcionistaService{

	@Autowired
	RepositorioRecepcionista repRecepcionista; 
	
	@Override
	public List<Recepcionista> buscarTodosLosRecepcionistas(){
		return repRecepcionista.findAll();
	}
	
	@Override
	public Optional<Recepcionista> buscarRecepcionistaPorId(int id) {
		return repRecepcionista.findById(id);
	}
	 
	@Override
	public boolean guardar(Recepcionista recepcionista) {
		Optional<Recepcionista> recepcionistaOptional = repRecepcionista.findRecepcionistaByRutRecepcionista(recepcionista.getRutRecepcionista());
		if(recepcionistaOptional.isPresent()) {
			return false;
		}
		repRecepcionista.saveAndFlush(recepcionista);
		return true;
	}

	@Override
	public boolean borrarRecepcionistaPorId(int id) {
		Optional<Recepcionista> recepcionistaOptional = repRecepcionista.findById(id);
		if(recepcionistaOptional.isPresent()) {
			repRecepcionista.deleteById(id);;
			return true;
		}
		return false;
	}
	
	@Override
    public boolean actualizarRecepcionista(Recepcionista recepcionista) {
		Optional<Recepcionista> recepcionistaOptional = repRecepcionista.findById(recepcionista.getId_recepcionista());
		if(recepcionistaOptional.isPresent()) {
			repRecepcionista.saveAndFlush(recepcionista);
			return true;
		}
		return false;
    }
	
	@Override
	public boolean existById(int id) {
		return repRecepcionista.existsById(id);
	}
}
