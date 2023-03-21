package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ClienteService;
import com.example.demo.service.ContratoService;
import com.example.demo.model.Cliente;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    ClienteService clienteService;
    @Autowired
    ContratoService contratoService;

    @GetMapping("")
    public ResponseEntity<List<Cliente>> mostrarTodos() {
        List<Cliente> clienteList = clienteService.buscarTodosLosClientes();
        if (!clienteList.isEmpty()) {
        	return new ResponseEntity<>(clienteList, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id_cliente}")
    public ResponseEntity<Cliente> mostrarId(@PathVariable int id_cliente) {
        Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(id_cliente);
        System.out.println("Wenas2");
        System.out.println(clienteOptional.get().toString());
        if(clienteOptional.isPresent()) {
			return new ResponseEntity<>(clienteOptional.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "")
    public ResponseEntity<Void> addCliente(@RequestBody Cliente cliente) {
        boolean creado = clienteService.saveCliente(cliente);
        if (creado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping
    public ResponseEntity<Void> editCliente(@RequestBody Cliente cliente){
    	boolean editado = clienteService.actualizarCliente(cliente);
        if (editado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping(value = "/{id_cliente}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable int id_cliente) {
    	int isRemoved = clienteService.borrarClientePorId(id_cliente);

        if (isRemoved==1) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }else if (isRemoved == 0) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
