package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.*;
import com.example.demo.repository.RepositorioCliente;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    RepositorioCliente repCliente;
    
    @Autowired
    RecepcionistaService recepcionistaService;
    
    @Override
    public List<Cliente> buscarTodosLosClientes() {
        return repCliente.findAll();
    }

    @Override
    public Optional<Cliente> buscarClientePorId(int id) {
        return repCliente.findById(id);
    }


    @Override
    public int borrarClientePorId(int id) {
        Optional<Cliente> clienteOptional = repCliente.findById(id);
        if(clienteOptional.isPresent()) {
        	if(!tieneContrato(id)) {
        		repCliente.borrarById(id);
        		return 1;
        	}else {
        		return 2;
        	}
        }
        return 0;
    }
    
    @Override
    public boolean actualizarCliente(Cliente cliente) {
    	if(recepcionistaService.buscarRecepcionistaPorId(cliente.getRecepcionista().getId_recepcionista()).isPresent()) {
    		Optional<Cliente> clienteOptional = repCliente.findClienteByRutCliente(cliente.getRutCliente());
    		if(clienteOptional.isPresent()) {
        		repCliente.saveAndFlush(cliente);
        		return true;
        	}
    	}
    	return false;
    }
    
    @Override
    public boolean saveCliente(Cliente cliente) {
    	if(recepcionistaService.buscarRecepcionistaPorId(cliente.getRecepcionista().getId_recepcionista()).isPresent()) {
    		Optional<Cliente> clienteOptional = repCliente.findClienteByRutCliente(cliente.getRutCliente());
    		if(clienteOptional.isPresent()) {
    			return false;
    		}else {
    			repCliente.saveAndFlush(cliente);
    			return true;
    		}
    	}
    	return false;  
    }
    
    @Override
    public boolean tieneContrato(int id) {
    	Optional<Cliente> clienteOptional = buscarClientePorId(id);
    	if(clienteOptional.isEmpty()) {
    		return false;
    	}
    	Contrato contrato = clienteOptional.get().getLastContrato();
    	if(contrato != null) {
    		if(contrato.getFecha().plusDays(contrato.getSuscripcion().getDuracion()).isAfter(LocalDate.now())){
    			return true;
    		}
       	}
    	return false;
    } 
    
    @Override
    public boolean tieneContratoByRut (String rut) {
    	Optional<Cliente> clienteOptional = repCliente.findClienteByRutCliente(rut);
    	if(clienteOptional.isEmpty()) {
    		return false;
    	}
    	Contrato contrato = clienteOptional.get().getLastContrato();
    	if(contrato != null) {
    		if(contrato.getFecha().plusDays(contrato.getSuscripcion().getDuracion()).isAfter(LocalDate.now())){
    			return true;
    		}
       	}
    	return false;
    } 
    
    @Override
    public boolean existById(int id) {
    	return repCliente.existsById(id);
    }
}
