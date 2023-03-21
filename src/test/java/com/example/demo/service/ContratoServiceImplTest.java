package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.example.demo.repository.RepositorioContrato;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Cliente;
import com.example.demo.model.Contrato;
import com.example.demo.model.Metodo_Pago;
import com.example.demo.model.Suscripcion;

@ExtendWith(MockitoExtension.class)
class ContratoServiceImplTest {

	@Mock
	private Contrato contrato;

	@Mock
	private ClienteService clienteService;

	@Mock
	private RepositorioContrato repContrato;

	@InjectMocks
	private ContratoServiceImpl contratoService;

	@Test
	void siInvocoInformacionDePagoConUnIdClienteConSuscripcionPagadaConTarjetaDeCreditoDebeRetornarOptionalConInformacionDePago() {
		// Arrange
		Cliente cliente = getCliente();
		cliente.getLastContrato().setMetodoPago(getMetodoPagoTarjetaCredito());
		when(clienteService.buscarClientePorId(1)).thenReturn(Optional.of(cliente));
		when(clienteService.tieneContrato(1)).thenReturn(true);

		Optional<Map<String, Object>> resultado;

		// Act
		resultado = contratoService.informacionPago(1);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(resultado.get().get("suscripcion"), cliente.getLastContrato().getSuscripcion());
		assertEquals(resultado.get().get("fecha_renovacion"),
				getContrato().getFecha().plusDays(getSuscripcion().getDuracion()));
		assertEquals(resultado.get().get("fecha_pago"), getContrato().getFecha().plusDays(30));

	}

	@Test
	void siInvocoInformacionDePagoConUnIdClienteConSuscripcionPagadaConEfectivoDebeRetornarOptionalConInformacionDePago() {
		// Arrange
		Cliente cliente = getCliente();
		cliente.getLastContrato().setMetodoPago(getMetodoPagoEfectivo());
		when(clienteService.buscarClientePorId(1)).thenReturn(Optional.of(cliente));
		when(clienteService.tieneContrato(1)).thenReturn(true);

		Optional<Map<String, Object>> resultado;

		// Act
		resultado = contratoService.informacionPago(1);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(resultado.get().get("suscripcion"), cliente.getLastContrato().getSuscripcion());
		assertEquals(resultado.get().get("fecha_renovacion"),
				getContrato().getFecha().plusDays(getSuscripcion().getDuracion()));
		assertEquals(resultado.get().get("fecha_pago"), null);
	}

	@Test
	void siInvocoInformacionDePagoConUnIdClienteSinContratoValidoDebeRetornarOptionalVacio() {
		// Arrange
		Cliente cliente = getCliente();
		when(clienteService.buscarClientePorId(1)).thenReturn(Optional.of(cliente));
		when(clienteService.tieneContrato(1)).thenReturn(false);

		Optional<Map<String, Object>> resultado;

		// Act
		resultado = contratoService.informacionPago(1);

		// Assert
		assertTrue(resultado.isEmpty());
	}

	@Test
	void siTratoDeEliminarUnContratoAnexadoAUnClienteSinUnPlanVigenteElContratoNoEsEliminado() {
		// Arrange
		Cliente cliente = getCliente();
		LocalDate d = LocalDate.now().plusYears(10); // R E V I S A R
		cliente.getLastContrato().setFecha(d);
		when(repContrato.findById(cliente.getId_cliente())).thenReturn(Optional.of(cliente.getLastContrato()));

		// Act

		boolean resultado;
		resultado = contratoService.borrarContratoPorId(cliente.getId_cliente());

		// Assert

		assertFalse(resultado);
	}

	@Test
	void siTratoDeEliminarUnContratoAnexadoAUnClienteConUnPlanVigenteElContratoEsEliminado(){
		// Arrange
		Cliente cliente = getCliente();
		LocalDate d = LocalDate.now().minusYears(10); // R E V I S A R
		cliente.getLastContrato().setFecha(d);
		when(repContrato.findById(cliente.getId_cliente())).thenReturn(Optional.of(cliente.getLastContrato()));

		// Act

		boolean resultado;
		resultado = contratoService.borrarContratoPorId(cliente.getId_cliente());

		// Assert

		assertTrue(resultado);
	}

	private Cliente getCliente() {
		Cliente cliente = new Cliente();
		cliente.setId_cliente(1);
		cliente.setRutCliente("20079145-2");
		cliente.setNombre("Pablo");
		cliente.setTelefono("945458080");
		cliente.setDeuda(0);
		cliente.setDireccion("La Cabaña 122");
		cliente.setContratos(Arrays.asList(getContrato()));
		return cliente;
	}

	private Contrato getContrato() {
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

	private Metodo_Pago getMetodoPagoTarjetaCredito() {
		Metodo_Pago metodoPago = new Metodo_Pago();
		metodoPago.setId_metodoPago(1);
		metodoPago.setNombre("Tarjeta Credito");
		return metodoPago;
	}

	private Metodo_Pago getMetodoPagoEfectivo() {
		Metodo_Pago metodoPago = new Metodo_Pago();
		metodoPago.setId_metodoPago(3);
		metodoPago.setNombre("Efectivo");
		return metodoPago;
	}
}