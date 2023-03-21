package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Contrato;

public interface RepositorioContrato extends JpaRepository <Contrato, Integer> {

}
