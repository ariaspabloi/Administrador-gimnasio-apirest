package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Sugerencia;

public interface SugerenciaService {

    public List<Sugerencia> buscarTodasLasSugerencias();

    public Optional<Sugerencia> buscarSugerenciaPorId(int id);

    public boolean guardarSugerencia(Sugerencia sugerencia);

    public boolean borrarSugerenciaPorId(int id);
    
    public boolean actualizarSugerencia(Sugerencia sugerencia);
    
    public List<Sugerencia> reporteSugerencia();

}
