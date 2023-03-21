package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Suscripcion;

@Repository
@Transactional
public interface RepositorioSuscripcion extends JpaRepository <Suscripcion, Integer> {
	Optional<Suscripcion> findSuscripcionByNombre(String nombre);
}
