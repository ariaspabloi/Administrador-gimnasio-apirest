package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "suscripciones")
public class Suscripcion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Suscripcion")
	private int id_suscripcion;
	private String nombre;
	private int valor;
	private int duracion;

	@ManyToMany
	@JoinTable(name = "incluyen_taller", joinColumns = @JoinColumn(name = "ID_Suscripcion"), inverseJoinColumns = @JoinColumn(name = "ID_Taller"))
	private List<Taller> talleres;

	@OneToMany(mappedBy = "suscripcion")
	@JsonBackReference
	private List<Contrato> contratos;

	// Getters

	public int getId_suscripcion() {
		return id_suscripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public int getValor() {
		return valor;
	}

	public int getDuracion() {
		return duracion;
	}

	public List<Taller> getTalleres() {
		if (talleres == null) {
			talleres = new ArrayList<>();
		}
		return talleres;
	}

	// Setters
	public void setId_suscripcion(int id_suscripcion) {
		this.id_suscripcion = id_suscripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public void setTalleres(List<Taller> talleres) {
		this.talleres = talleres;
	}

	public void addTaller(Taller taller) {
		getTalleres().add(taller);
		taller.addSuscripcion(this);
	}

}
