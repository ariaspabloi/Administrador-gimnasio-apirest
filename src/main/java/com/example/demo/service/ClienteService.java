package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Cliente;

public interface ClienteService {
    
    public List<Cliente> buscarTodosLosClientes();

    public Optional<Cliente> buscarClientePorId(int id);

    public int borrarClientePorId(int id);
    
    public boolean actualizarCliente(Cliente cliente);

    public boolean saveCliente(Cliente cliente);
    
    public boolean tieneContrato(int id);
    
    public boolean tieneContratoByRut(String rut);
    
    public boolean existById(int id);
}
