package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Cita;

public interface CitaService {
	
	public List<Cita> buscarTodasLasCitas();

    public Optional<Cita> buscarCitaPorId(int id);

    public boolean guardar(Cita cita);
    
    public boolean actualizar(Cita cita);

    public int borrarCitaPorId(int id);
}
