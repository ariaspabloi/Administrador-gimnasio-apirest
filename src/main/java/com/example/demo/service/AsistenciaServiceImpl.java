package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.*;
import com.example.demo.repository.RepositorioAsistencia;

@Service
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService{
	@Autowired
	RepositorioAsistencia repAsistencia;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	RecepcionistaService recepcionistaService;
	
	@Override
	public List<Asistencia> buscarTodasLasAsistencias(){
		return repAsistencia.findAll();
	}

	@Override
	public List<Asistencia> todayAsistencias(){
		LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
		List<Asistencia> listaDeHoy = repAsistencia.findAll();
		listaDeHoy.removeIf(c ->
			!c.getFecha_entrada().truncatedTo(ChronoUnit.DAYS).equals(today));
		return listaDeHoy;
	}

	@Override
	public List<Asistencia> reporteDia(LocalDate dia){
		LocalDateTime diaTime = dia.atStartOfDay();
		List<Asistencia> listaDelDia = repAsistencia.findAll();
		listaDelDia.removeIf(c ->
			!c.getFecha_entrada().truncatedTo(ChronoUnit.DAYS).equals(diaTime));
		return listaDelDia;
		
	}
	
	@Override
	public List<List<Asistencia>> reporte(LocalDate inicio, LocalDate fin){
		List<List<Asistencia>> elReporte = new ArrayList<>();
		for(LocalDate i = inicio; i.isBefore(fin); i=i.plusDays(1) )
			elReporte.add(reporteDia(i));
		return elReporte;
	}
	
	@Override
	public boolean saveAsistencia(Asistencia asistencia) {
		if(!recepcionistaService.existById(asistencia.getRecepcionista().getId_recepcionista())) {
			return false;
		}
		if(clienteService.tieneContratoByRut(asistencia.getRut_cliente())) {
			asistencia.setFecha_entrada(LocalDateTime.now());
			asistencia.setFecha_salida(LocalDateTime.now().plusHours(1));
			repAsistencia.saveAndFlush(asistencia);
			return true;
		}
		
		return false;
	}
	
	@Override
	public int deleteAsistencia(int id_asistencia) {
		if(repAsistencia.existsById(id_asistencia)) {
			LocalDateTime fechaEntrada = repAsistencia.findById(id_asistencia).get().getFecha_entrada().plusMinutes(6);
			LocalDateTime fechaActual = LocalDateTime.now();
			if(fechaEntrada.isAfter(fechaActual)) {
				repAsistencia.deleteById(id_asistencia);
				return 1;
			}else {
				return 2;
			}
		}
		return 0;
	}

}
