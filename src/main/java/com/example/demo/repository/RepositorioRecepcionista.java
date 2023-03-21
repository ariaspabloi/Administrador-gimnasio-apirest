package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Recepcionista;

@Repository
@Transactional
public interface RepositorioRecepcionista extends JpaRepository <Recepcionista, Integer> {
	Optional<Recepcionista> findRecepcionistaByRutRecepcionista(String rut_recepcionista);
}
