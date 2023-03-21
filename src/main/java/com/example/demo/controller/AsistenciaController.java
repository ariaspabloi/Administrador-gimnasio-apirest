package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Asistencia;
import com.example.demo.service.AsistenciaService;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    AsistenciaService asistenciaService;

    @GetMapping("")
    public ResponseEntity<List<Asistencia>> getAllAsistencias() {
        List<Asistencia> asistenciaList = asistenciaService.buscarTodasLasAsistencias();
        if (!asistenciaList.isEmpty()) {
            return new ResponseEntity<>(asistenciaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/reporte")
    public ResponseEntity<List<Asistencia>> getReporteDiario(){
        List<Asistencia> asistenciaList = asistenciaService.todayAsistencias();
        if (!asistenciaList.isEmpty()) {
            return new ResponseEntity<>(asistenciaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/reporte/{dia}")
    public ResponseEntity<List<Asistencia>> getReporte(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia){
        List<Asistencia> asistenciaList = asistenciaService.reporteDia(dia);
        if (!asistenciaList.isEmpty()) {
            return new ResponseEntity<>(asistenciaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/reporte/{inicio}/{fin}")
    public ResponseEntity<List< List<Asistencia> >> getReporte(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                                               @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){
        List<List<Asistencia>> asistenciaList = asistenciaService.reporte(inicio,fin);
        if (!asistenciaList.isEmpty()) {
            return new ResponseEntity<>(asistenciaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "")
    public ResponseEntity<Void> addAsistencia(@RequestBody Asistencia asistencia) {
        boolean creado = asistenciaService.saveAsistencia(asistencia);
        if (creado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping(value = "/{id_asistencia}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable int id_asistencia) {
    	int isRemoved = asistenciaService.deleteAsistencia(id_asistencia);

        if (isRemoved==1) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }else if (isRemoved == 0) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
