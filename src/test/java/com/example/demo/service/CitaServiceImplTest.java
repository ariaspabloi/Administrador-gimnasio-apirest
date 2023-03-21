package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.RepositorioCita;
import com.example.demo.repository.RepositorioCliente;
import com.example.demo.repository.RepositorioEntrenador;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class CitaServiceImplTest {

    @Mock
    private RepositorioCita repCita;

    @Mock
    private ClienteService clienteService;

    @Mock
    private EntrenadorService entrenadorService;

    @InjectMocks
    private CitaServiceImpl citaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void siInvocoGuardarCitaYHayHoraYSuscripcionRetornaTrue() {
    	Cita cita = getCita();
    	cita.setFecha(LocalDateTime.now().minusDays(1));
    	
    	when(clienteService.existById(anyInt())).thenReturn(true);
    	when(entrenadorService.buscarEntrenadorPorId(anyInt())).thenReturn(Optional.of(cita.getEntrenador()));
    	when(clienteService.tieneContrato(anyInt())).thenReturn(true);
    	when(repCita.saveAndFlush(cita)).thenReturn(cita);
    	when(repCita.findById(anyInt())).thenReturn(Optional.of(cita));
    	
    	boolean resultado;
    	resultado = citaService.guardar(cita);
    	
    	assertTrue(resultado);
    }
    
    @Test
    void siInvocoGuardarCitaYNoHayHoraYSiSuscripcionRetornaFalse() {
    	Cita cita = getCita();
    	cita.setFecha(LocalDateTime.now().minusDays(1));
    	Cita citaProgramada = getCita();
    	citaProgramada.setFecha(LocalDateTime.now().minusDays(1));
    	Set<Cita> citasProgramadas = new HashSet<>();
    	citasProgramadas.add(citaProgramada);
    	cita.getEntrenador().setCitas(citasProgramadas);
    	
    	when(clienteService.existById(anyInt())).thenReturn(true);
    	when(entrenadorService.buscarEntrenadorPorId(anyInt())).thenReturn(Optional.of(cita.getEntrenador()));
    	when(clienteService.tieneContrato(anyInt())).thenReturn(true);
    	
    	boolean resultado;
    	resultado = citaService.guardar(cita);
    	
    	assertFalse(resultado);
    }
    
    @Test
    void siInvocoGuardarCitaYNoHaySuscripcionRetornaFalse() {
    	Cita cita = getCita();
    	
    	when(clienteService.existById(anyInt())).thenReturn(true);
    	when(entrenadorService.buscarEntrenadorPorId(anyInt())).thenReturn(Optional.of(cita.getEntrenador()));
    	when(clienteService.tieneContrato(anyInt())).thenReturn(false);
    	
    	boolean resultado;
    	resultado = citaService.guardar(cita);
    	
    	assertFalse(resultado);
    }
    
    @Test
    void siInvocoGuardarCitaYNoExisteElClienteRetornaFalse() {
    	Cita cita = getCita();
    	when(clienteService.existById(anyInt())).thenReturn(false);
    	
    	boolean resultado;
    	resultado = citaService.guardar(cita);
    	
    	assertFalse(resultado);
    }
    
    @Test
    void siInvocoGuardarCitaYNoExisteEntrenadorRetornaFalse() {
    	Cita cita = getCita();
    	
    	when(clienteService.existById(anyInt())).thenReturn(true);
    	when(entrenadorService.buscarEntrenadorPorId(anyInt())).thenReturn(Optional.empty());
    	
    	boolean resultado;
    	resultado = citaService.guardar(cita);
    	
    	assertFalse(resultado);
    }

    @Test
    void siIntentoBorrarLaCitaCuandoQuedanMenosDe24HorasElSistemaNoLoHace(){
        Cita cita = getCita();
        cita.setFecha(LocalDateTime.now().plusHours(5));
        when(repCita.findById(1)).thenReturn(Optional.of(cita));

        int resultado = citaService.borrarCitaPorId(1);

        assertTrue(resultado == 0);
    }

    @Test
    void siIntentoBorrarLaCitaCuandoQuedanMasDe24HorasElSistemaLoHaceYRetornaMensajeCorrespondiente(){
        Cita cita = getCita();
        cita.setFecha(LocalDateTime.now().plusDays(25));
        when(repCita.findById(1)).thenReturn(Optional.of(cita));

        int resultado = citaService.borrarCitaPorId(1);

        assertTrue(resultado == 1);
    }

    @Test
    void siIntentoBorrarLaCitaCuandoEstaNoEstaVigenteElSistemaNoLoHace(){
        Cita cita = getCita();
        cita.setFecha(LocalDateTime.now().minusDays(25));
        when(repCita.findById(1)).thenReturn(Optional.of(cita));

        int resultado = citaService.borrarCitaPorId(1);

        assertTrue(resultado == 0);
    }

    @Test
    void siIntentoBorrarLaCitaCuandoNoExisteElSistemaNoLoHaceYRetornaErrorCorrespondiente(){
        Optional <Cita> cita = Optional.empty();
        when(repCita.findById(1)).thenReturn(cita);

        int resultado = citaService.borrarCitaPorId(1);

        assertTrue(resultado == 2);
    }

    private Cliente getCliente(){
        ArrayList<Contrato> contratos = new ArrayList<>();
        contratos.add(getContrato());

        Cliente cliente = new Cliente();
        cliente.setId_cliente(1);
        cliente.setRutCliente("20079145-2");
        cliente.setNombre("Pablo");
        cliente.setTelefono("945458080");
        cliente.setDeuda(0);
        cliente.setDireccion("La Cabaña 122");
        cliente.setContratos(contratos);
        return cliente;
    }

    private Entrenador getEntrenador(){
        Entrenador entrenador = new Entrenador();
        entrenador.setId_entrenador(1);
        entrenador.setRut_entrenador("20.267.059-8");
        entrenador.setTelefono("953536698");
        entrenador.setNombre("Ignacio Aldea");
        return entrenador;
    }

    private Contrato getContrato(){
        Contrato contrato = new Contrato();
        contrato.setId_contrato(1);
        contrato.setFecha(LocalDate.now());
        contrato.setSuscripcion(getSuscripcion());
        return contrato;
    }

    private Suscripcion getSuscripcion() {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setId_suscripcion(3);
        suscripcion.setNombre("Trimestral");
        suscripcion.setValor(42000);
        suscripcion.setDuracion(180);
        return suscripcion;
    }
    
    private Cita getCita() {
    	Cita cita = new Cita();
    	cita.setDuracion(60);
    	cita.setCliente(new Cliente());
    	cita.setEntrenador(new Entrenador());
    	return cita;
    }
}