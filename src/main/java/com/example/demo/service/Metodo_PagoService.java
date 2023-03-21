package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Metodo_Pago;

public interface Metodo_PagoService {

	public List<Metodo_Pago> buscarTodosLosMetodo_Pago();

    public Optional<Metodo_Pago> buscarMetodo_PagoPorId(int id);

    public boolean guardar(Metodo_Pago metodo_Pago);

    public boolean borrarMetodo_PagoPorId(int id);
    
    public boolean existById(int id);
    
    public boolean actualizarMetodoPago(Metodo_Pago metodo_Pago);
}
