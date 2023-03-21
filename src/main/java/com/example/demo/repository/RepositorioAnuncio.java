package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Anuncio;

public interface RepositorioAnuncio extends JpaRepository <Anuncio, Integer>{

}
