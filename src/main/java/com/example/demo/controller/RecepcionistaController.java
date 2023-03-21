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

import com.example.demo.model.Recepcionista;
import com.example.demo.service.RecepcionistaService;

@RestController
@RequestMapping("/recepcionistas")
public class RecepcionistaController {

	@Autowired
	RecepcionistaService recepcionistaService;
	
	@GetMapping("")
    public ResponseEntity<List<Recepcionista>> mostrarTodos() {
        List<Recepcionista> recepcionistaList = recepcionistaService.buscarTodosLosRecepcionistas();
        if (!recepcionistaList.isEmpty()) {
        	return new ResponseEntity<>(recepcionistaList, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id_recepcionista}")
    public ResponseEntity<Recepcionista> mostrarId(@PathVariable int id_recepcionista) {
        Optional<Recepcionista> recepcionistaOptional = recepcionistaService.buscarRecepcionistaPorId(id_recepcionista);
        if(recepcionistaOptional.isPresent()) {
			return new ResponseEntity<>(recepcionistaOptional.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "")
    public ResponseEntity<Void> addRecepcionista(@RequestBody Recepcionista recepcionista) {
        boolean creado = recepcionistaService.guardar(recepcionista);
        if (creado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping
    public ResponseEntity<Void> editRecepcionista(@RequestBody Recepcionista recepcionista){
    	boolean editado = recepcionistaService.actualizarRecepcionista(recepcionista);
        if (editado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping(value = "/{id_recepcionista}")
    public ResponseEntity<Void> deleteRecepcionistaById(@PathVariable int id_recepcionista) {
    	boolean isRemoved = recepcionistaService.borrarRecepcionistaPorId(id_recepcionista);

        if (isRemoved) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }else{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
