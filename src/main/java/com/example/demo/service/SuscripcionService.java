package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Suscripcion;

public interface SuscripcionService {

    public List<Suscripcion> buscarTodasLasSuscripciones();

    public Optional<Suscripcion> buscarSuscripcionPorId(int id);

    public boolean guardar(Suscripcion suscripcion);

    public boolean borrarSuscripcionPorId(int id);
    
    public boolean existById(int id);
    
    public boolean actualizarSuscripcion(Suscripcion suscripcion);
}
