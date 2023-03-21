package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "entrenadores")
public class Entrenador {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Entrenador")   
    private int id_entrenador;
    private String nombre;
    private String rutEntrenador;
    private String telefono;
    @OneToMany(mappedBy="entrenador")
    private Set<Cita> citas = new HashSet<>();

    public Set<Cita> getCitas() {
		return citas;
	}
	public void setCitas(Set<Cita> citas) {
		this.citas = citas;
	}
	//Getters
    public int getId_entrenador() {
		return id_entrenador;
	}
	public String getNombre() {
		return nombre;
	}
	public String getRut_entrenador() {
		return rutEntrenador;
	}
	public String getTelefono() {
		return telefono;
	}

    //Setters
	public void setId_entrenador(int id_entrenador) {
		this.id_entrenador = id_entrenador;
	}
	public void setNombre(String nombre) {
        this.nombre = nombre;
    } 
	public void setRut_entrenador(String rut_entrenador) {
		this.rutEntrenador = rut_entrenador;
	} 
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
