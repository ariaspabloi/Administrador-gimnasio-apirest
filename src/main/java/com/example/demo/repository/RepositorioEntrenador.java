package com.example.demo.repository;

import com.example.demo.model.Entrenador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEntrenador extends JpaRepository<Entrenador, Integer>{

	Optional<Entrenador> findEntrenadorByRutEntrenador(String rut);
	
	
	
}
