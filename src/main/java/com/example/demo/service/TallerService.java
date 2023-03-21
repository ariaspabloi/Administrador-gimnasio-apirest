package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Taller;

public interface TallerService {

    public List<Taller> buscarTodosLosTalleres();

    public Optional<Taller> buscarTallerPorId(int id);

    public boolean guardar(Taller taller);

    public boolean borrarTallerPorId(int id);
    
    public boolean actualizarTaller(Taller taller);
    
    public List<Taller> reporteMasAsistidos();
}
