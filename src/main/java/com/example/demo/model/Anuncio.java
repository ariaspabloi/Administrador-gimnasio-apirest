package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "anuncio")
public class Anuncio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Anuncio")
	private int id_anuncio;
    private LocalDate fecha;
    private String informacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_Recepcionista")
    private Recepcionista recepcionista;
    
    //Getters
	public int getId_anuncio() {
		return id_anuncio;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public String getInformacion() {
		return informacion;
	}
	public Recepcionista getRecepcionista() {
		return recepcionista;
	}
	
	//Setters
	public void setId_anuncio(int id_anuncio) {
		this.id_anuncio = id_anuncio;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	public void setRecepcionista(Recepcionista recepcionista) {
		this.recepcionista = recepcionista;
	}
    
}

