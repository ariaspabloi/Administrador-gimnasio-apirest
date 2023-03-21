package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Taller;
import com.example.demo.repository.RepositorioTaller;

@Service
@Transactional
public class TallerServiceImpl implements TallerService{

	@Autowired
	RepositorioTaller repTaller;
	
	@Override
    public List<Taller> buscarTodosLosTalleres(){
    	return repTaller.findAll();
    }

    @Override
    public Optional<Taller> buscarTallerPorId(int id) {
    	return repTaller.findById(id);
    }

    @Override
    public boolean guardar(Taller taller) {
    	Optional<Taller> tallerOptional = repTaller.findTallerByNombre(taller.getNombre());
    	if(tallerOptional.isPresent()) {
    		return false;
    	}
    	repTaller.saveAndFlush(taller);
    	return true;
    }
    
    @Override
    public boolean borrarTallerPorId(int id) {
    	Optional<Taller> tallerOptional = repTaller.findById(id);
    	if(tallerOptional.isPresent()) {
    		repTaller.deleteById(id);
    		return true;
    	}
    	return false;
    }
    
    @Override
    public boolean actualizarTaller(Taller taller) {
    	Optional<Taller> tallerOptional = repTaller.findById(taller.getId_taller());
    	if(tallerOptional.isPresent()) {
    		repTaller.saveAndFlush(taller);
        	return true;
    	}
    	return false;
    }
    
    @Override
    public List<Taller> reporteMasAsistidos(){
    	List<Taller> talleresMasAsistidos = new ArrayList<Taller>();
    	List<Taller> talleresVigentes = repTaller.findAll();
		boolean asistencia = false;
    	for(int i = talleresVigentes.size(); i<talleresVigentes.size(); i++){

			if(talleresVigentes.get(i).getAsistentes() != 0){
				asistencia = true;
			}

		}

		if(asistencia) {
			talleresVigentes.sort(Comparator.comparing(Taller::getAsistentes));

			for (int i = talleresVigentes.size() - 3; i < talleresVigentes.size(); i++) {
				talleresMasAsistidos.add(talleresVigentes.get(i));
			}
			return talleresMasAsistidos;
		}
		return talleresMasAsistidos;
    }
}
