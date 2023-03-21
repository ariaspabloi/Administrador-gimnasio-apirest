package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.model.Contrato;

public interface ContratoService {

    public List<Contrato> buscarTodosLosContratos();

    public Optional<Contrato> buscarContratoPorId(int id);

    public boolean borrarContratoPorId(int id);

    public List<Contrato> reporteMensual();
	
    public boolean saveContrato(Contrato contrato);
    
    public boolean editContrato(Contrato contrato);
    
    public Optional<Map<String, Object>> informacionPago(int id);
    
    public LocalDate fechaCobro(Contrato contrato);
    
    public LocalDate fechaRenovacion(Contrato contrato);
}
