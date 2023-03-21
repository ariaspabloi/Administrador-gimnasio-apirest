package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.Asistencia;

public interface AsistenciaService {
	
    public List<Asistencia> buscarTodasLasAsistencias();
    
    public List<Asistencia> todayAsistencias();
    
    public List<Asistencia> reporteDia(LocalDate dia);

    public List<List<Asistencia>> reporte(LocalDate inicio, LocalDate fin);

    public boolean saveAsistencia(Asistencia asistencia);
    
    public int deleteAsistencia(int id_asistencia);
}
