package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "metodos_pago")
public class Metodo_Pago {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_metodoPago;
	private String nombre;
	
	@OneToMany(mappedBy = "metodoPago")
    @JsonBackReference
    private List<Contrato> contratos;
	
	//Getters 
	public int getId_metodoPago() {
		return id_metodoPago;
	}
	public String getNombre() {
		return nombre;
	}
	
	//Setters
	public void setId_metodoPago(int id_metodoPago) {
		this.id_metodoPago = id_metodoPago;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
