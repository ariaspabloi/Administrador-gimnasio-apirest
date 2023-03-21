package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Cita;

public interface RepositorioCita extends JpaRepository <Cita, Integer> {

}
