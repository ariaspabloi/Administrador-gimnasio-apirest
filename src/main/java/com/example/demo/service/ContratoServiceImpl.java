package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.*;
import com.example.demo.repository.RepositorioContrato;

@Service
@Transactional
public class ContratoServiceImpl implements ContratoService {

	@Autowired
	RepositorioContrato repContrato;
	@Autowired
	ClienteService clienteService;
	@Autowired
	SuscripcionService suscripcionService;
	@Autowired
	Metodo_PagoService metodoPagoService;

	@Override
	public List<Contrato> buscarTodosLosContratos() {
		return repContrato.findAll();
	}

	@Override
	public Optional<Contrato> buscarContratoPorId(int id) {
		return repContrato.findById(id);
	}

	@Override
	public boolean borrarContratoPorId(int id) {
		Optional<Contrato> contratoOptional = repContrato.findById(id);
		LocalDate fechaContrato = contratoOptional.get().getFecha();
		LocalDate fechaActual = LocalDate.now();
		if (contratoOptional.isPresent()) {
			if(fechaActual.isAfter(fechaContrato.plusDays(contratoOptional.get().getSuscripcion().getDuracion()))) {
				repContrato.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean saveContrato(Contrato contrato) {
		// Revisar si existe el cliente
		if (!clienteService.existById(contrato.getCliente().getId_cliente())) {
			return false;
		}
		// Revisar si existe la suscripcion
		if (!suscripcionService.existById(contrato.getSuscripcion().getId_suscripcion())) {
			return false;
		}
		// Revisar si existe el metodo de pago
		if (!metodoPagoService.existById(contrato.getMetodoPago().getId_metodoPago())) {
			return false;
		}
		// Si tiene contrato, adicionar dias restantes de la suscripcion al nuevo
		// contrato
		Cliente cliente = clienteService.buscarClientePorId(contrato.getCliente().getId_cliente()).get();
		if (clienteService.tieneContrato(cliente.getId_cliente())) {
			LocalDate fechaUltimoContrato = cliente.getLastContrato().getFecha();
			LocalDate fechaActual = LocalDate.now();
			int duracionUltimoContrato = suscripcionService
					.buscarSuscripcionPorId(contrato.getSuscripcion().getId_suscripcion()).get().getValor();
			LocalDate fechaNuevoContrato = fechaActual
					.plusDays(duracionUltimoContrato - fechaUltimoContrato.until(fechaActual, ChronoUnit.DAYS));
			contrato.setFecha(fechaNuevoContrato);
		} else {
			contrato.setFecha(LocalDate.now());
		}
		// Crear contrato
		repContrato.saveAndFlush(contrato);
		Optional<Contrato> contratoOptional = repContrato.findById(contrato.getId_contrato());
		// Si el metodo de pago es credito, actualizar deuda
		if (contrato.getMetodoPago().getId_metodoPago() == 1) {
			cliente.setDeuda(cliente.getDeuda() + suscripcionService
					.buscarSuscripcionPorId(contrato.getSuscripcion().getId_suscripcion()).get().getValor());
			clienteService.actualizarCliente(cliente);
		}
		return contratoOptional.isPresent();
	}

	@Override
	public List<Contrato> reporteMensual() {
		ArrayList<Contrato> contratos = (ArrayList<Contrato>) buscarTodosLosContratos();
		LocalDate ultimoMes = LocalDate.now().minusDays(30);
		contratos.removeIf(c -> c.getFecha().isBefore(ultimoMes));
		return contratos;
	}

	@Override
	public boolean editContrato(Contrato contrato) {
		// Revisar si existe el cliente
		if (!clienteService.existById(contrato.getCliente().getId_cliente())) {
			return false;
		}
		// Revisar si existe la suscripcion
		if (!suscripcionService.existById(contrato.getSuscripcion().getId_suscripcion())) {
			return false;
		}
		// Revisar si existe el metodo de pago
		if (!metodoPagoService.existById(contrato.getMetodoPago().getId_metodoPago())) {
			return false;
		}
		// Crear contrato
		repContrato.saveAndFlush(contrato);
		Optional<Contrato> contratoOptional = repContrato.findById(contrato.getId_contrato());
		// Si el metodo de pago es credito, actualizar deuda
		if (contrato.getMetodoPago().getId_metodoPago() == 1) {
			Cliente cliente = clienteService.buscarClientePorId(contrato.getCliente().getId_cliente()).get();
			cliente.setDeuda(cliente.getDeuda() + suscripcionService
					.buscarSuscripcionPorId(contrato.getSuscripcion().getId_suscripcion()).get().getValor());
			clienteService.actualizarCliente(cliente);
		}
		return contratoOptional.isPresent();
	}

	@Override
	public Optional<Map<String, Object>> informacionPago(int id_cliente) {
		Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(id_cliente);
		if (clienteOptional.isEmpty())
			return Optional.empty();
		if (!clienteService.tieneContrato(id_cliente))
			return Optional.empty();
		Contrato contrato = clienteOptional.get().getLastContrato();
		if (contrato == null)
			return Optional.empty();
		Suscripcion suscripcion = contrato.getSuscripcion();
		LocalDate fechaRenovacion = this.fechaRenovacion(contrato);
		LocalDate fechaCobro = this.fechaCobro(contrato);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("suscripcion", suscripcion);
		responseMap.put("fecha_renovacion", fechaRenovacion);
		responseMap.put("fecha_pago", fechaCobro);
		return Optional.of(responseMap);
	}

	@Override
	public LocalDate fechaCobro(Contrato contrato) {
		Suscripcion suscripcion = contrato.getSuscripcion();
		Metodo_Pago metodoPago = contrato.getMetodoPago();
		LocalDate fechaCobro = null;
		if (metodoPago.getNombre().equals("Tarjeta Credito")) {
			LocalDate hoy = LocalDate.now();
			for (int i = 30; i <= suscripcion.getDuracion(); i = i + 30) {
				LocalDate fechaCuota = contrato.getFecha().plusDays(i);
				if (hoy.isBefore(fechaCuota) || hoy.isEqual(fechaCuota)) {
					fechaCobro = fechaCuota;
					break;
				}
			}
		}
		return fechaCobro;
	}

	@Override
	public LocalDate fechaRenovacion(Contrato contrato) {
		Suscripcion suscripcion = contrato.getSuscripcion();
		LocalDate fechaRenovacion = contrato.getFecha().plusDays(suscripcion.getDuracion());
		return fechaRenovacion;
	}

}
