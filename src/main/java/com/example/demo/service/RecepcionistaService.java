package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Recepcionista;

public interface RecepcionistaService {

	 public List<Recepcionista> buscarTodosLosRecepcionistas();

	 public Optional<Recepcionista> buscarRecepcionistaPorId(int id);
	 
	 public boolean guardar(Recepcionista recepcionista);

	 public boolean borrarRecepcionistaPorId(int id);

	 public boolean actualizarRecepcionista(Recepcionista recepcionista);
	 
	 public boolean existById(int id);
}
