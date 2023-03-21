package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
@Table(name = "citas")
public class Cita {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Cita")
	private int id_cita;
	private String estado;
	@Column
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fecha;
	private int duracion;
	
    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    @JsonIdentityInfo(
    		  generator = ObjectIdGenerators.PropertyGenerator.class, 
    		  property = "id_cliente")
    @JsonIdentityReference(alwaysAsId=true)
	private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "ID_Entrenador")
    @JsonIdentityInfo(
  		  generator = ObjectIdGenerators.PropertyGenerator.class, 
  		  property = "id_entrenador")
    @JsonIdentityReference(alwaysAsId=true)
	private Entrenador entrenador;
	
	//Getters
	public int getId_cita() {
		return id_cita;
	}
	public String getEstado() {
		return estado;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public int getDuracion() {
		return duracion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public Entrenador getEntrenador() {
		return entrenador;
	}
	
	
	//Setters
	public void setId_cita(int id_cita) {
		this.id_cita = id_cita;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}
}
