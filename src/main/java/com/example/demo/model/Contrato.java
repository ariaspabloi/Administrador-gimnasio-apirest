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


@Entity
@Table(name = "contratos")
public class Contrato {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Contrato")
	private int id_contrato;
	
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente")
    @JsonBackReference
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "ID_Suscripcion")
    private Suscripcion suscripcion;
    
    @ManyToOne
    @JoinColumn(name = "ID_MetodoPago")
	private Metodo_Pago metodoPago;
    
	//Getters
	
	public int getId_contrato() {
		return id_contrato;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public Suscripcion getSuscripcion() {
		return suscripcion;
	}
	public Metodo_Pago getMetodoPago() {
		return metodoPago;
	}
	
	
	
	//Setters
	public void setId_contrato(int id_contrato) {
		this.id_contrato = id_contrato;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setSuscripcion(Suscripcion suscripcion) {
		this.suscripcion = suscripcion;
	}
	public void setMetodoPago(Metodo_Pago metodoPago) {
		this.metodoPago = metodoPago;
	}
	
}
