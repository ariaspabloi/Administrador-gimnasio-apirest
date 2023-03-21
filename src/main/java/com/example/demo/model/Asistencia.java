package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "asistencias")
public class Asistencia{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Asistencia_ID")
	private int id_asistencia;
	private String rut_cliente;
	private LocalDateTime fecha_entrada;
	private LocalDateTime fecha_salida;
	@ManyToOne
	@JoinColumn(name = "ID_Recepcionista")
	@JsonIdentityInfo(
			generator = ObjectIdGenerators.PropertyGenerator.class,
			property = "id_recepcionista")
	@JsonIdentityReference(alwaysAsId=true)
	private Recepcionista recepcionista;

	//Getters
	public int getId_asistencia() {
		return id_asistencia;
	}
	public String getRut_cliente() {
		return rut_cliente;
	}
	public LocalDateTime getFecha_entrada() {
		return fecha_entrada;
	}
	public LocalDateTime getFecha_salida() {
		return fecha_salida;
	}
	public Recepcionista getRecepcionista() {
		return recepcionista;
	}

	//Setters
	public void setId_asistencia(int id_asistencia) {
		this.id_asistencia = id_asistencia;
	}
	public void setRut_cliente(String rut_cliente) {
		this.rut_cliente = rut_cliente;
	}
	public void setFecha_entrada(LocalDateTime fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}
	public void setFecha_salida(LocalDateTime fecha_salida) {
		this.fecha_salida = fecha_salida;
	}
	public void setRecepcionista(Recepcionista recepcionista) {
		this.recepcionista = recepcionista;
	}
}

