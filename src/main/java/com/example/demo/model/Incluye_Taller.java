package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "incluyen_taller")
public class Incluye_Taller {

	private int id_suscripcion;
	private int id_taller;
	
	//Getters
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId_suscripcion() {
		return id_suscripcion;
	}
	public int getId_taller() {
		return id_taller;
	}
	
	//Setters
	public void setId_suscripcion(int id_suscripcion) {
		this.id_suscripcion = id_suscripcion;
	}
	public void setId_taller(int id_taller) {
		this.id_taller = id_taller;
	}	
}
