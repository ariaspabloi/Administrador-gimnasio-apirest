package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Anuncio;

public interface AnuncioService {
	
	public List<Anuncio> buscarTodosLosAnuncios();
	
	public Optional<Anuncio> buscarAnuncioPorId(int id);

    public boolean guardar(Anuncio anuncio);

    public boolean borrarAnuncioPorId(int id);
    
    public boolean actualizarAnuncio(Anuncio anuncio);
}

