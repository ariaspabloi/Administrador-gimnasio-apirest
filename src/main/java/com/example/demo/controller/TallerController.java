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

import com.example.demo.model.Taller;
import com.example.demo.service.TallerService;

@RestController
@RequestMapping("/talleres")
public class TallerController {

	@Autowired
	TallerService tallerService;
	
	@GetMapping("")
	public ResponseEntity<List<Taller>> getAllTalleres() {
        List<Taller> tallerList = tallerService.buscarTodosLosTalleres();
        if (!tallerList.isEmpty()) {
            return new ResponseEntity<>(tallerList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{idTaller}")
	public ResponseEntity<Taller> getTallerById(@PathVariable int idTaller) {
		Optional<Taller> tallerOptional = tallerService.buscarTallerPorId(idTaller);
		if(tallerOptional.isPresent()) {
			return new ResponseEntity<>(tallerOptional.get(), HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/reporte")
	public ResponseEntity<List<Taller>> reporte() {
        List<Taller> tallerList = tallerService.reporteMasAsistidos();
        if (!tallerList.isEmpty()) {
            return new ResponseEntity<>(tallerList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping(value = "")
	public ResponseEntity<Void> addTaller(@RequestBody Taller taller){
		boolean creado = tallerService.guardar(taller);
		if(creado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Void> updateTaller(@RequestBody Taller taller){
		boolean actualizado = tallerService.actualizarTaller(taller);
		if(actualizado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{idTaller}")
	public ResponseEntity<Void> deleteTaller(@PathVariable int idTaller){
		boolean eliminado = tallerService.borrarTallerPorId(idTaller);
		if(eliminado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
