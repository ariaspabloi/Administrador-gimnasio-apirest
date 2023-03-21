package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cita;
import com.example.demo.model.Entrenador;
import com.example.demo.repository.RepositorioEntrenador;

@Service
@Transactional
public class EntrenadorServiceImpl implements EntrenadorService{
	
	@Autowired
    RepositorioEntrenador repEntrenador;

    @Override
    public List<Entrenador> buscarTodosLosEntrenadores() {
        
        return repEntrenador.findAll();
    }

    @Override
    public Optional<Entrenador> buscarEntrenadorPorId(int id) {
        return repEntrenador.findById(id);
    }

    @Override
    public boolean guardar(Entrenador entrenador) {
        Optional<Entrenador> entrenadorOptional = repEntrenador.findEntrenadorByRutEntrenador(entrenador.getRut_entrenador());
        if(entrenadorOptional.isPresent()){
        	return false;
        }
        repEntrenador.saveAndFlush(entrenador);
        return true;
    }
    
    @Override
    public boolean actualizar(Entrenador entrenador) {
    	Optional<Entrenador> entrenadorOptional = repEntrenador.findById(entrenador.getId_entrenador());
    	if(entrenadorOptional.isPresent()) {
    		repEntrenador.saveAndFlush(entrenador);
    		return true;
    	}else{
    		return false;
    	}
    }

    @Override
    public boolean borrarEntrenadorPorId(int id) {
    	Optional<Entrenador> entrenadorOptional = repEntrenador.findById(id);
    	if(entrenadorOptional.isPresent()) {
    		repEntrenador.deleteById(id);
    		return true;
    	}else{
    		return false;
    	}
    }
    
    @Override
    public Set<Cita> getAgendaEntrenadorById(int id) {
    	Set<Cita> citas = new HashSet<>();
    	Optional<Entrenador> entrenadorOptional = repEntrenador.findById(id);
    	if(entrenadorOptional.isPresent()) {
    		citas = entrenadorOptional.get().getCitas();
    	}
    	return citas;
    }
}
