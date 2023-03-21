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

import com.example.demo.model.Cita;
import com.example.demo.service.CitaService;

@RestController
@RequestMapping("/citas")
public class CitaController {

	@Autowired
	CitaService citaService;
	
	@GetMapping("")
	public ResponseEntity<List<Cita>> getAllCitas() {
        List<Cita> citaList = citaService.buscarTodasLasCitas();
        if (!citaList.isEmpty()) {
            return new ResponseEntity<>(citaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{idCita}")
	public ResponseEntity<Cita> getCitaById(@PathVariable int idCita) {
		Optional<Cita> citaOptional = citaService.buscarCitaPorId(idCita);
		if(citaOptional.isPresent()) {
			return new ResponseEntity<>(citaOptional.get(), HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PostMapping("")
	public ResponseEntity<Void> addCita(@RequestBody Cita cita){
		boolean creado = citaService.guardar(cita);
		if(creado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Void> updateCita(@RequestBody Cita cita){
		boolean actualizado = citaService.actualizar(cita);
		if(actualizado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{idCita}")
	public ResponseEntity<Void> deleteCita(@PathVariable int idCita){
		int eliminado = citaService.borrarCitaPorId(idCita);
		if(eliminado == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else if (eliminado == 2) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
