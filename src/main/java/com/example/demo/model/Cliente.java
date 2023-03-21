package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "clientes")
public class Cliente {
	//falta testear los post
	// falta testear gets de cliente y de recepcionista
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Cliente")
	private int id_cliente;
	private String rutCliente;
    private String nombre;
    private String telefono;
    private int deuda = 0;
    private String direccion;
    
    @OneToMany(mappedBy = "cliente")
    @JsonBackReference
    private List<Contrato> contratos;
    
    @OneToMany(mappedBy = "cliente")
    @JsonIdentityInfo(
    		  generator = ObjectIdGenerators.PropertyGenerator.class, 
    		  property = "id_sugerencia")
    @JsonIdentityReference(alwaysAsId=true)
    private List<Sugerencia> sugerencias;
    
    @ManyToOne
    @JoinColumn(name = "ID_Recepcionista")
    @JsonIdentityInfo(
  		  generator = ObjectIdGenerators.PropertyGenerator.class, 
  		  property = "id_recepcionista")
    @JsonIdentityReference(alwaysAsId=true)
    private Recepcionista recepcionista;
    
    //Getters
	
	public int getId_cliente() {
        return id_cliente;
    }
    public String getNombre() {
        return nombre;
    }
    public String getRutCliente() {
        return rutCliente;
    }
    public String getTelefono() {
        return telefono;
    }
    public int getDeuda() {
        return deuda;
    }
    public String getDireccion() {
        return direccion;
    }
    
	public Recepcionista getRecepcionista() {
		return recepcionista;
	}
    
    public Contrato getLastContrato() {
    	if(contratos.size() >=1) {
    		int indexLast = contratos.size() - 1;
        	return contratos.get(indexLast);
    	}
    	return null;
    }
    public List<Contrato> getContratos() {
		return contratos;
	}
    public List<Sugerencia> getSugerencias() {
		return sugerencias;
	}

    //Setters
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setRutCliente(String rut_cliente) {
        this.rutCliente = rut_cliente;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setDeuda(int deuda) {
        this.deuda = deuda;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
	
	public void setRecepcionista(Recepcionista recepcionista) {
		this.recepcionista = recepcionista;
	}
	
	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	public void setSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias = sugerencias;
	}
}
