package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.example.demo.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.repository.RepositorioEntrenador;

@ExtendWith(MockitoExtension.class)
class EntrenadorServiceImplTest {
	

	@Mock
    RepositorioEntrenador repEntrenador;

	@InjectMocks
	private EntrenadorServiceImpl entrenadorService;

	@Test
	void siInvocoGuardarEntrenadroYYaExisteNoSeGuardaraYRetornaFalso() {
		Entrenador entrenador = getEntrenador();
		when(repEntrenador.findEntrenadorByRutEntrenador(entrenador.getRut_entrenador())).thenReturn(Optional.of(entrenador));
		
		boolean resultado;
		resultado = entrenadorService.guardar(entrenador);
		
		assertFalse(resultado);
	}
	
	@Test
	void siInvocoGuardarEntrenadroYNoExisteSeGuardaraYRetornaVerdadero() {
		Entrenador entrenador = getEntrenador();
		when(repEntrenador.findEntrenadorByRutEntrenador(entrenador.getRut_entrenador())).thenReturn(Optional.empty());
		
		boolean resultado;
		resultado = entrenadorService.guardar(entrenador);
		
		assertTrue(resultado);
	}

	@Test
	void siConsultoHorasAgendadasAUnEntrenadorSinNingunaSeRetornaListaVacia(){
		// Arrange
		Entrenador entrenador = getEntrenador();
		when(repEntrenador.findById(1)).thenReturn(Optional.of(entrenador));

		Set<Cita> resultado;

		// Act
		resultado = entrenadorService.buscarEntrenadorPorId(1).get().getCitas();

		// Asset
		assertTrue(resultado.isEmpty());
	}

	@Test
	void siConsultoHorasAgendadasAUnEntrenadorQuePoseeEntoncesRetornaListaDeCitas(){
		// Arrange
		Entrenador entrenador = getEntrenador();
		entrenador.setCitas(getSetCitas());
		when(repEntrenador.findById(1)).thenReturn(Optional.of(entrenador));

		Set<Cita> Citas;

		// Act
		Citas = entrenadorService.buscarEntrenadorPorId(1).get().getCitas();

		// Assert
		assertTrue(Citas.equals(entrenador.getCitas()));

	}

	private Entrenador getEntrenador() {
		Entrenador entrenador = new Entrenador();
		entrenador.setNombre("Juanito");
		entrenador.setRut_entrenador("20.079.145-2");
		entrenador.setTelefono("945459090");
		entrenador.setId_entrenador(1);
		return entrenador;
	}

	private Set<Cita> getSetCitas(){

		Cita c1 = new Cita();
		c1.setEntrenador(getEntrenador());
		c1.setId_cita(1);
		c1.setEstado("Pendiente");
		c1.setDuracion(25);
		c1.setCliente(getCliente());
		c1.setFecha(LocalDateTime.now().plusDays(3));

		Cita c2 = new Cita();
		c2.setEntrenador(getEntrenador());
		c2.setId_cita(2);
		c2.setEstado("Pendiente");
		c2.setDuracion(30);
		c2.setCliente(getCliente());
		c2.setFecha(LocalDateTime.now().plusDays(6));

		Set<Cita> citas = new HashSet<>();
		citas.add(c1);
		citas.add(c2);

		return citas;
	}

	private Cliente getCliente(){
		Cliente cliente = new Cliente();
		cliente.setId_cliente(1);

		return cliente;
	}
}