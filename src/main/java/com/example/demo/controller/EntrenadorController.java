package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Cita;
import com.example.demo.model.Entrenador;
import com.example.demo.service.EntrenadorService;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {

	@Autowired
	EntrenadorService entrenadorService; 
	
	@GetMapping("")
	public ResponseEntity<List<Entrenador>> getAllEntrenadores() {
        List<Entrenador> entrenadorList = entrenadorService.buscarTodosLosEntrenadores();
        if (!entrenadorList.isEmpty()) {
            return new ResponseEntity<>(entrenadorList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{idEntrenador}")
	public ResponseEntity<Entrenador> getEntrenadorById(@PathVariable int idEntrenador) {
		Optional<Entrenador> entrenadorOptional = entrenadorService.buscarEntrenadorPorId(idEntrenador);
		if(entrenadorOptional.isPresent()) {
			return new ResponseEntity<>(entrenadorOptional.get(), HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/agenda/{idEntrenador}")
	public ResponseEntity<Set<Cita>> getAgendaEntrenadorById(@PathVariable int idEntrenador) {
		Set<Cita> citas = entrenadorService.getAgendaEntrenadorById(idEntrenador);
		if(!citas.isEmpty()) {
			return new ResponseEntity<>(citas, HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PostMapping("")
	public ResponseEntity<Void> addEntrenador(@RequestBody Entrenador entrenador){
		boolean creado = entrenadorService.guardar(entrenador);
		if(creado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Void> updateEntrenador(@RequestBody Entrenador entrenador){
		boolean actualizado = entrenadorService.actualizar(entrenador);
		if(actualizado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{idEntrenador}")
	public ResponseEntity<Void> deleteEntrenador(@PathVariable int idEntrenador){
		boolean eliminado = entrenadorService.borrarEntrenadorPorId(idEntrenador);
		if(eliminado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}
