package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Asistencia;

public interface RepositorioAsistencia extends JpaRepository <Asistencia, Integer> {

}
