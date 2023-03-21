package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Metodo_Pago;

@Repository
@Transactional
public interface RepositorioMetodo_Pago extends JpaRepository <Metodo_Pago, Integer>{

	Optional<Metodo_Pago> findMetodoPagoByNombre(String nombre);
}
