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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "sugerencias")
public class Sugerencia {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Sugerencia")
	private int id_sugerencia;
    private LocalDate fecha;
    private String detalle;
    private int estado;
    
    
    @ManyToOne
    @JoinColumn(name = "ID_Recepcionista")
    @JsonBackReference
	private Recepcionista recepcionista;

    @JoinColumn(name = "ID_Cliente")
    @ManyToOne()
    @JsonIdentityInfo(
    		  generator = ObjectIdGenerators.PropertyGenerator.class, 
    		  property = "id_cliente")
    @JsonIdentityReference(alwaysAsId=true)
	private Cliente cliente;


	//Getters  
	public int getId_sugerencia() {
		return id_sugerencia;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	public String getDetalle() {
		return detalle;
	}
	public int getEstado() {
		return estado;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public Recepcionista getRecepcionista() {
		return recepcionista;
	}
	
	
	//Setters
	public void setId_sugerencia(int id_sugerencia) {
		this.id_sugerencia = id_sugerencia;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public void setRecepcionista(Recepcionista recepcionista) {
		this.recepcionista = recepcionista;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	
}
