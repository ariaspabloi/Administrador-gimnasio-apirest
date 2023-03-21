package com.example.demo.repository;

import com.example.demo.model.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RepositorioCliente extends JpaRepository <Cliente, Integer> {
	Optional<Cliente> findClienteByRutCliente(String rut_cliente);
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM clientes WHERE ID_Cliente = :id", nativeQuery = true)
	int borrarById(@Param("id") int id);
}
