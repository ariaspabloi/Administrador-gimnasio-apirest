package com.example.demo.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "recepcionistas")
public class Recepcionista {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Recepcionista")
	private int id_recepcionista;
	private String rutRecepcionista;
	private String horarioLaboral;
	
	@OneToMany(mappedBy = "recepcionista")
    @JsonManagedReference
    @JsonIdentityInfo(
  		  generator = ObjectIdGenerators.PropertyGenerator.class, 
  		  property = "id_sugerencia")
	@JsonIdentityReference(alwaysAsId=true)
    private List<Sugerencia> sugerencias;
	@OneToMany(mappedBy = "recepcionista")
    @JsonIdentityInfo(
    		  generator = ObjectIdGenerators.PropertyGenerator.class, 
    		  property = "id_cliente")
	@JsonIdentityReference(alwaysAsId=true)
    private List<Cliente> clientes;

	@OneToMany(mappedBy = "recepcionista")
	@JsonBackReference
	@JsonIdentityInfo(
  		  generator = ObjectIdGenerators.PropertyGenerator.class, 
  		  property = "id_asistencia")
	@JsonIdentityReference(alwaysAsId=true)
	private List<Asistencia> asistencias;
	
	@OneToMany(mappedBy = "recepcionista")
	@JsonIdentityInfo(
	  		  generator = ObjectIdGenerators.PropertyGenerator.class, 
	  		  property = "id_anuncio")
	@JsonIdentityReference(alwaysAsId=true)
	private List<Anuncio> anuncios;
	
	//Getters
	public int getId_recepcionista() {
		return id_recepcionista;
	}
	public String getRutRecepcionista() {
		return rutRecepcionista;
	}
	public String getHorarioLaboral() {
		return horarioLaboral;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public List<Sugerencia> getSugerencias() {
		return sugerencias;
	}
	
	//Setters
	public void setId_recepcionista(int id_recepcionista) {
		this.id_recepcionista = id_recepcionista;
	}
	public void setRutRecepcionista(String rutRecepcionista) {
		this.rutRecepcionista = rutRecepcionista;
	}
	public void setHorarioLaboral(String horarioLaboral) {
		this.horarioLaboral = horarioLaboral;
	}
	
	public void setSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias = sugerencias;
	}
	
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}
