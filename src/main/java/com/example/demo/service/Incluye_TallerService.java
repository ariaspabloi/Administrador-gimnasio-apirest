package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Incluye_Taller;

public interface Incluye_TallerService {
    public List<Incluye_Taller> buscarTodosLosIncluye_Taller();

    public Incluye_Taller BuscarIncluye_TallerPorId(int id);

    public void Guardar(Incluye_Taller incluye_Taller);

    public void BorrarIncluye_TallerPorId(int id);
}
