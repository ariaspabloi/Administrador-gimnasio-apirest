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

import com.example.demo.model.Suscripcion;
import com.example.demo.service.SuscripcionService;

@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {
	
	@Autowired
	SuscripcionService suscripcionService;
	
	@GetMapping("")
	public ResponseEntity<List<Suscripcion>> getAllSuscripciones() {
        List<Suscripcion> suscripcionList = suscripcionService.buscarTodasLasSuscripciones();
        if (!suscripcionList.isEmpty()) {
            return new ResponseEntity<>(suscripcionList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{idSuscripcion}")
	public ResponseEntity<Suscripcion> getSuscripcionById(@PathVariable int idSuscripcion) {
		Optional<Suscripcion> suscripcionOptional = suscripcionService.buscarSuscripcionPorId(idSuscripcion);
		if(suscripcionOptional.isPresent()) {
			return new ResponseEntity<>(suscripcionOptional.get(), HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	@PostMapping(value = "")
	public ResponseEntity<Void> addSuscripcion(@RequestBody Suscripcion suscripcion){
		boolean creado = suscripcionService.guardar(suscripcion);
		if(creado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Void> updateSuscripcion(@RequestBody Suscripcion suscripcion){
		boolean actualizado = suscripcionService.actualizarSuscripcion(suscripcion);
		if(actualizado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{idSuscripcion}")
	public ResponseEntity<Void> deleteSuscripcion(@PathVariable int idSuscripcion){
		boolean eliminado = suscripcionService.borrarSuscripcionPorId(idSuscripcion);
		if(eliminado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
