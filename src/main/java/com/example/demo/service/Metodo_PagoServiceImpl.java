package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Metodo_Pago;
import com.example.demo.repository.RepositorioMetodo_Pago;

@Service
@Transactional
public class Metodo_PagoServiceImpl implements Metodo_PagoService{

	@Autowired
	RepositorioMetodo_Pago repMetodo_Pago;
	
	@Override
	public List<Metodo_Pago> buscarTodosLosMetodo_Pago(){
		return repMetodo_Pago.findAll();
	}

	@Override
    public Optional<Metodo_Pago> buscarMetodo_PagoPorId(int id) {
    	return repMetodo_Pago.findById(id);
    }

    @Override
    public boolean guardar(Metodo_Pago metodo_Pago) {
    	Optional<Metodo_Pago> metodoPagoOptional = repMetodo_Pago.findMetodoPagoByNombre(metodo_Pago.getNombre());
    	if(metodoPagoOptional.isPresent()) {
    		return false;
    	}
    	repMetodo_Pago.saveAndFlush(metodo_Pago);
		return true;
    }

    @Override
    public boolean borrarMetodo_PagoPorId(int id) {
    	Optional<Metodo_Pago> metodoPagoOptional = repMetodo_Pago.findById(id);
    	if(metodoPagoOptional.isPresent()) {
    		repMetodo_Pago.deleteById(id);;
    		return true;
    	}
    	return false;
    }
    
    @Override
    public boolean existById(int id) {
    	return repMetodo_Pago.existsById(id);
    }
    
    @Override
    public boolean actualizarMetodoPago(Metodo_Pago metodoPago) {
    	Optional<Metodo_Pago> metodoPagoOptional = repMetodo_Pago.findById(metodoPago.getId_metodoPago());
    	if(metodoPagoOptional.isPresent()) {
    		repMetodo_Pago.saveAndFlush(metodoPago);
    		return true;
    	}
    	return false;
    }
}
