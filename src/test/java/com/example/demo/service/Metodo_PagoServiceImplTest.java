package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.demo.model.Metodo_Pago;
import com.example.demo.repository.RepositorioMetodo_Pago;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class Metodo_PagoServiceImplTest {

    @Mock
    RepositorioMetodo_Pago repMetodoPago;

    @InjectMocks
    private Metodo_PagoServiceImpl metodoPagoService;

    @Test
    void siConsultoMetodosDePagoCuandoLosHayRetornaLosMetodosDePagoDisponibles(){
        // Arrange
        Metodo_Pago metodo = getMetodo();
        List<Metodo_Pago> lista = List.of(metodo);
        when(repMetodoPago.findAll()).thenReturn(lista);
        // Act
        List<Metodo_Pago> resultado = metodoPagoService.buscarTodosLosMetodo_Pago();

        // Assert
        assertTrue(resultado.get(0).getId_metodoPago()==(getMetodo().getId_metodoPago()));
        assertTrue(resultado.get(0).getNombre().equals(getMetodo().getNombre()));

    }

    @Test
    void siConsultoMetodosDePagoCuandoNoLosHayRetornaError(){
        // Arrange

        // Act
        List<Metodo_Pago> resultado = metodoPagoService.buscarTodosLosMetodo_Pago();

        // Assert
        assertTrue(resultado.isEmpty());
    }

    private Metodo_Pago getMetodo(){
        Metodo_Pago metodo = new Metodo_Pago();
        metodo.setId_metodoPago(1);
        metodo.setNombre("tarjeta");
        return metodo;
    }
}