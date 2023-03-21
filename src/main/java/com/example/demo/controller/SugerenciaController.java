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

import com.example.demo.model.Sugerencia;
import com.example.demo.service.SugerenciaService;

@RestController
@RequestMapping("/sugerencias")
public class SugerenciaController {

	@Autowired
	SugerenciaService sugerenciaService;
	
	@GetMapping("")
	public ResponseEntity<List<Sugerencia>> getAllSugerencias() {
        List<Sugerencia> sugerenciaList = sugerenciaService.buscarTodasLasSugerencias();
        if (!sugerenciaList.isEmpty()) {
            return new ResponseEntity<>(sugerenciaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{idSugerencia}")
	public ResponseEntity<Sugerencia> getSugerenciaById(@PathVariable int idSugerencia) {
		Optional<Sugerencia> sugerenciaOptional = sugerenciaService.buscarSugerenciaPorId(idSugerencia);
		if(sugerenciaOptional.isPresent()) {
			return new ResponseEntity<>(sugerenciaOptional.get(), HttpStatus.OK);
		}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/reporte")
	public ResponseEntity<List<Sugerencia>> getReporteSugerencia() {
        List<Sugerencia> sugerenciaList = sugerenciaService.reporteSugerencia();
        if (!sugerenciaList.isEmpty()) {
            return new ResponseEntity<>(sugerenciaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping(value = "")
	public ResponseEntity<Void> addSugerencia(@RequestBody Sugerencia sugerencia){
		boolean creado = sugerenciaService.guardarSugerencia(sugerencia);
		if(creado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Void> updateSugerencia(@RequestBody Sugerencia sugerencia){
		boolean actualizado = sugerenciaService.actualizarSugerencia(sugerencia);
		if(actualizado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{idSugerencia}")
	public ResponseEntity<Void> deleteSugerencia(@PathVariable int idSugerencia){
		boolean eliminado = sugerenciaService.borrarSugerenciaPorId(idSugerencia);
		if(eliminado) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
