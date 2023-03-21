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

import com.example.demo.model.Metodo_Pago;
import com.example.demo.service.Metodo_PagoService;

@RestController
@RequestMapping("/metodos_pago")
public class Metodo_PagoController {

	@Autowired
	Metodo_PagoService metodo_PagoService;
	
	@GetMapping("")
    public ResponseEntity<List<Metodo_Pago>> mostrarTodos() {
		List<Metodo_Pago> metodoPagoList = metodo_PagoService.buscarTodosLosMetodo_Pago();
        if (!metodoPagoList.isEmpty()) {
        	return new ResponseEntity<>(metodoPagoList, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id_metodoPago}")
    public ResponseEntity<Metodo_Pago> mostrarId(@PathVariable int id_metodoPago) {
        Optional<Metodo_Pago> metodoPagoOptional = metodo_PagoService.buscarMetodo_PagoPorId(id_metodoPago);
        if(metodoPagoOptional.isPresent()) {
			return new ResponseEntity<>(metodoPagoOptional.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "")
    public ResponseEntity<Void> addMetodoPago(@RequestBody Metodo_Pago metodoPago) {
        boolean creado = metodo_PagoService.guardar(metodoPago);
        if (creado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping
    public ResponseEntity<Void> editMetodoPago(@RequestBody Metodo_Pago metodoPago){
    	boolean editado = metodo_PagoService.actualizarMetodoPago(metodoPago);
        if (editado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping(value = "/{id_cliente}")
    public ResponseEntity<Void> deleteMetodoPagoById(@PathVariable int id_metodoPago) {
    	boolean isRemoved = metodo_PagoService.borrarMetodo_PagoPorId(id_metodoPago);

        if (isRemoved) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }else{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
