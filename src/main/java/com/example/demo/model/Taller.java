package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

	
@Entity
@Table(name = "talleres")
public class Taller {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Taller")
	private int id_taller;
    private String nombre;
    private int cupos;
    private int asistentes;
    private String horario;
    
    
    @ManyToMany(mappedBy = "talleres")
    private List<Suscripcion> suscripciones;
    
    
    //Getters
    
	public int getId_taller() {
		return id_taller;
	}
	public String getNombre() {
		return nombre;
	}
	public int getCupos() {
		return cupos;
	}
	public int getAsistentes() {
		return asistentes;
	}
	
	
	@JsonIgnore
    public List<Suscripcion> getSuscripciones() {
        if (suscripciones == null) {
            suscripciones = new ArrayList<>();
        }
        return suscripciones;
    }
	
	//Setters
	public String getHorario() {
		return horario;
	}
	public void setId_taller(int id_taller) {
		this.id_taller = id_taller;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCupos(int cupos) {
		this.cupos = cupos;
	}
	public void setAsistentes(int asistentes) {
		this.asistentes = asistentes;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	@JsonIgnore
    public void setSuscripciones(List<Suscripcion> suscripciones) {
        this.suscripciones = suscripciones;
    }

    public void addSuscripcion(Suscripcion suscripcion) {
        getSuscripciones().add(suscripcion);
    }
   
}
