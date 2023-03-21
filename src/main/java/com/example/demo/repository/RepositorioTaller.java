package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Taller;

@Repository
@Transactional
public interface RepositorioTaller extends JpaRepository <Taller, Integer> {
	Optional<Taller> findTallerByNombre(String nombre);
}
