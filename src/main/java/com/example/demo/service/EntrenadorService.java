package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.model.Cita;
import com.example.demo.model.Entrenador;

public interface EntrenadorService {
    
    public List<Entrenador> buscarTodosLosEntrenadores();

    public Optional<Entrenador> buscarEntrenadorPorId(int id);

    public boolean guardar(Entrenador Entrenador);
    
    public boolean actualizar(Entrenador entrenador);

    public boolean borrarEntrenadorPorId(int id);
    
    public Set<Cita> getAgendaEntrenadorById(int id);

}
