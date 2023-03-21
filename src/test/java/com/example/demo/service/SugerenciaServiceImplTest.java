package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.demo.model.Cliente;
import com.example.demo.model.Recepcionista;
import com.example.demo.model.Sugerencia;
import com.example.demo.repository.RepositorioSugerencia;


@ExtendWith(MockitoExtension.class)
public class SugerenciaServiceImplTest {

	@Mock
	private Sugerencia sugerencia;
	
	@Mock
	private ClienteService clienteService;
	
	@Mock
	private RecepcionistaService recepcionistaService;
	
	@Mock
	private RepositorioSugerencia repSugerencia;
	
	@InjectMocks
	private SugerenciaServiceImpl sugerenciaServiceImpl;
	
	@Test
	void siIngresoSiendoClienteUnaSugerenciaConVeinteOMasCaracteresDebeGuardarLaSugerencia(){
		Sugerencia sugerencia= getSugerencia();
		sugerencia.setCliente(getCliente());
		sugerencia.setDetalle("12345678900987654321");
		when(clienteService.existById(sugerencia.getCliente().getId_cliente())).thenReturn(true);
		when(clienteService.tieneContrato(sugerencia.getCliente().getId_cliente())).thenReturn(true);
		when(repSugerencia.saveAndFlush(sugerencia)).thenReturn(null);
		when(repSugerencia.findById(sugerencia.getId_sugerencia())).thenReturn(Optional.of(sugerencia));
		boolean resultado;
		resultado=sugerenciaServiceImpl.guardarSugerencia(sugerencia);
		assertTrue(resultado);
	}
	
	@Test
	void siIngresoSiendoClienteUnaSugerenciaConMenosDeVeinteCaracteresNoDebeGuardarLaSugerencia(){
		Sugerencia sugerencia= getSugerencia();
		sugerencia.setCliente(getCliente());
		sugerencia.setDetalle("123");
		when(clienteService.existById(sugerencia.getCliente().getId_cliente())).thenReturn(true);
		when(clienteService.tieneContrato(sugerencia.getCliente().getId_cliente())).thenReturn(true);
		
		boolean resultado;
		resultado=sugerenciaServiceImpl.guardarSugerencia(sugerencia);
		assertFalse(resultado);
	}
	
	
	@Test
	void siIngresoSiendoRecepcionistaUnaSugerenciaConVeinteOMasCaracteresDebeGuardarLaSugerencia(){
		Sugerencia sugerencia= getSugerencia();
		sugerencia.setRecepcionista(getRecepcionista());
		sugerencia.setDetalle("12345678900987654321");
		when(recepcionistaService.existById(sugerencia.getRecepcionista().getId_recepcionista())).thenReturn(true);
		when(repSugerencia.saveAndFlush(sugerencia)).thenReturn(null);
		when(repSugerencia.findById(sugerencia.getId_sugerencia())).thenReturn(Optional.of(sugerencia));
		boolean resultado;
		resultado=sugerenciaServiceImpl.guardarSugerencia(sugerencia);
		assertTrue(resultado);
	}
	
	@Test
	void siIngresoSiendoRecepcionistaUnaSugerenciaConMenosDeVeinteCaracteresNoDebeGuardarLaSugerencia(){
		Sugerencia sugerencia= getSugerencia();
		sugerencia.setRecepcionista(getRecepcionista());
		sugerencia.setDetalle("123");
		when(recepcionistaService.existById(sugerencia.getRecepcionista().getId_recepcionista())).thenReturn(true);
		
		boolean resultado;
		resultado=sugerenciaServiceImpl.guardarSugerencia(sugerencia);
		assertFalse(resultado);
	}
	
	@Test
	void siPidoUnReporteDeSugerenciasYHaySugerenciasConEstadoPendienteEntoncesArrojaElListadoDeSugerenciasPendientes() {
		
		List<Sugerencia> sugerencias = getListadoSugerencias();
        when(repSugerencia.findAll()).thenReturn(sugerencias);
        List<Sugerencia> sugerenciasPendientes = sugerenciaServiceImpl.reporteSugerencia();

        assertNotNull(sugerenciasPendientes);
        assertEquals(sugerencias.get(0),sugerenciasPendientes.get(0));
	}
	
	@Test
	void siPidoUnReporteDeSugerenciasYNoHaySugerenciasConEstadoPendienteEntoncesArrojaNull() {
		List<Sugerencia> sugerencias = getListadoSugerenciasEstadoUno();
        when(repSugerencia.findAll()).thenReturn(sugerencias);
        List<Sugerencia> sugerenciasPendientes = sugerenciaServiceImpl.reporteSugerencia();

        assertNotNull(sugerenciasPendientes);
        assertTrue(sugerenciasPendientes.isEmpty());
	}
	
	private Sugerencia getSugerencia(){
		Sugerencia sugerencia=new Sugerencia();
		sugerencia.setFecha(LocalDate.now());
		sugerencia.setEstado(0);
		return sugerencia;
	}
	
	private Cliente getCliente() {
		Cliente cliente = new Cliente();
		cliente.setId_cliente(22);
		cliente.setRutCliente("20079145-2");
		cliente.setNombre("Pablo");
		cliente.setTelefono("945458080");
		cliente.setDeuda(0);
		cliente.setDireccion("La Cabaña 122");
		return cliente;
	}

	private Recepcionista getRecepcionista(){
		Recepcionista recepcionista= new Recepcionista();
		recepcionista.setId_recepcionista(2);
		recepcionista.setRutRecepcionista("20373323-2");
		return recepcionista;
	}
	
	private List<Sugerencia> getListadoSugerencias(){

        List<Sugerencia> sugerencias = new ArrayList<>();

        Sugerencia sugerencia = new Sugerencia();
        sugerencia.setDetalle("12345678900987654321");
        sugerencia.setEstado(0);
        sugerencias.add(sugerencia);
        
        sugerencia = new Sugerencia();
        sugerencia.setDetalle("12345678900987654321");
        sugerencia.setEstado(1);
        sugerencias.add(sugerencia);

        return sugerencias;

    }

	private List<Sugerencia> getListadoSugerenciasEstadoUno(){

        List<Sugerencia> sugerencias = new ArrayList<>();

        Sugerencia sugerencia = new Sugerencia();
        sugerencia.setDetalle("12345678900987654321");
        sugerencia.setEstado(1);
        sugerencias.add(sugerencia);
        
        sugerencia = new Sugerencia();
        sugerencia.setDetalle("12345678900987654321");
        sugerencia.setEstado(1);
        sugerencias.add(sugerencia);

        return sugerencias;

    }

	
}
