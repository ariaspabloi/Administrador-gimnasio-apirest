package com.example.demo.controller;

import java.util.List;
import java.util.Map;
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

import com.example.demo.model.Contrato;
import com.example.demo.service.ContratoService;

@RestController
@RequestMapping("/contratos")
public class ContratoController {
	
	@Autowired
	ContratoService contratoService;
	
	@GetMapping("")
	public ResponseEntity<List<Contrato>> buscarTodosLosContratos(){
		List<Contrato> contratoList = contratoService.buscarTodosLosContratos();
		if (!contratoList.isEmpty()) {
        	return new ResponseEntity<>(contratoList, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@GetMapping("/reporte")
	public List<Contrato> reporteMensual(){
		return contratoService.reporteMensual();
	}
	
	@GetMapping("/{id_contrato}")
	public ResponseEntity<Contrato> buscarContratoPorId(@PathVariable int id_contrato) {
		Optional<Contrato> contratoOptional = contratoService.buscarContratoPorId(id_contrato);
		if(contratoOptional.isPresent()) {
			return new ResponseEntity<>(contratoOptional.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PostMapping(value = "")
    public ResponseEntity<Void> addContrato(@RequestBody Contrato contrato) {
        boolean creado = contratoService.saveContrato(contrato);
        if (creado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

	@PutMapping
	public ResponseEntity<Void> editContrato(@RequestBody Contrato contrato){
		boolean editado = contratoService.editContrato(contrato);
        if (editado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
	}
	
	@DeleteMapping(value = "/{id_contrato}")
    public ResponseEntity<Void> deleteContratoById(@PathVariable int id_contrato) {
    	boolean isRemoved = contratoService.borrarContratoPorId(id_contrato);

        if (isRemoved) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
    @GetMapping("/informacionPago/{id_cliente}")
    public ResponseEntity<Object> informacionDePago(@PathVariable int id_cliente) {
    	Optional<Map<String,Object>> informacionPago = contratoService.informacionPago(id_cliente);
		if(informacionPago.isPresent()) {
			return new ResponseEntity<>(informacionPago.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
