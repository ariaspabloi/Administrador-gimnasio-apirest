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

import com.example.demo.model.Anuncio;
import com.example.demo.service.AnuncioService;

@RestController
@RequestMapping("/anuncio")
public class AnuncioController {

	@Autowired
	AnuncioService anuncioService;
	
	@GetMapping("")
	public ResponseEntity<List<Anuncio>> mostrarTodos(){
		List<Anuncio> anuncioList = anuncioService.buscarTodosLosAnuncios();
		if(!anuncioList.isEmpty()) {
			return new ResponseEntity<>(anuncioList, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@GetMapping("/{id_anuncio}")
	public ResponseEntity<Anuncio> mostrarPorId(@PathVariable int id_anuncio){
		Optional<Anuncio> anuncioOptional = anuncioService.buscarAnuncioPorId(id_anuncio);
		if(anuncioOptional.isPresent()) {
			return new ResponseEntity<>(anuncioOptional.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PostMapping(value = "")
	public ResponseEntity<Void> addAnuncio(@RequestBody Anuncio anuncio){
		boolean creado = anuncioService.guardar(anuncio);
		if(creado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<Void> editAnuncio(@RequestBody Anuncio anuncio){
		boolean editado = anuncioService.actualizarAnuncio(anuncio);
		if(editado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
	}
	
	@DeleteMapping(value = "{id_anuncio}")
	public ResponseEntity<Void> deleteAnuncioById(@PathVariable int id_anuncio){
		boolean isRemoved = anuncioService.borrarAnuncioPorId(id_anuncio);
		if(isRemoved) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
