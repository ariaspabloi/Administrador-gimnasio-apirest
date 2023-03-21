package com.example.demo.service;

import com.example.demo.model.Anuncio;
import com.example.demo.model.Recepcionista;
import com.example.demo.repository.RepositorioAnuncio;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AnuncioServiceImplTest {

    @Mock
    private RepositorioAnuncio repositorioAnuncio;
	
	@Mock
	private RecepcionistaService recepcionistaService;
    
    @InjectMocks
    private AnuncioServiceImpl anuncioService;

    @Test
    @DisplayName("buscarAnuncioPorIdTEST1")
    void SeCompruebaQueLaFuncionBuscarPorIdDevuelvaElAnuncioSolicitado() {
        Anuncio anuncio = getAnuncio();
		when(repositorioAnuncio.findById(1)).thenReturn(Optional.of(anuncio));

		int resultado = anuncioService.buscarAnuncioPorId(1).get().getId_anuncio();

		assertEquals(anuncio.getId_anuncio(), resultado);
    }

    @Test
    void siInvocoGuardarAnuncioConFechaIncorrectaRetornaFalso() {
    	Anuncio anuncio = getAnuncio();
    	anuncio.setFecha(LocalDate.now().minusDays(10));
    	when(recepcionistaService.buscarRecepcionistaPorId(anyInt())).thenReturn(Optional.of(new Recepcionista()));
    	when(repositorioAnuncio.findById(anyInt())).thenReturn(Optional.empty());
    	when(repositorioAnuncio.saveAndFlush(anuncio)).thenReturn(anuncio);
    	
    	boolean resultado;
    	resultado = anuncioService.guardar(anuncio);
    	
    	assertFalse(resultado);
    }

    @Test
    void siInvocoGuardarAnuncioConFechaCorrectaRetornaVerdadero() {
    	Anuncio anuncio = getAnuncio();
    	anuncio.setFecha(LocalDate.now().plusDays(10));
    	when(recepcionistaService.buscarRecepcionistaPorId(anyInt())).thenReturn(Optional.of(new Recepcionista()));
    	when(repositorioAnuncio.findById(anyInt())).thenReturn(Optional.empty());
    	when(repositorioAnuncio.saveAndFlush(anuncio)).thenReturn(anuncio);
    	
    	boolean resultado;
    	resultado = anuncioService.guardar(anuncio);
    	
    	assertTrue(resultado);
    }
    
    @Test
    void siInvocoGuardarAnuncioSinFechaRetornaVerdadero() {
    	Anuncio anuncio = getAnuncio();
    	when(recepcionistaService.buscarRecepcionistaPorId(anyInt())).thenReturn(Optional.of(new Recepcionista()));
    	when(repositorioAnuncio.findById(anyInt())).thenReturn(Optional.empty());
    	when(repositorioAnuncio.saveAndFlush(anuncio)).thenReturn(anuncio);
    	
    	boolean resultado;
    	resultado = anuncioService.guardar(anuncio);
    	
    	assertTrue(resultado);
    }
    
    @Test
    void siInvocoActualizarAnuncioYElAnuncioExistiaRetornoVerdadero() {
    	Anuncio anuncio = getAnuncio();
    	when(recepcionistaService.buscarRecepcionistaPorId(anyInt())).thenReturn(Optional.of(new Recepcionista()));
    	when(repositorioAnuncio.findById(anyInt())).thenReturn(Optional.of(new Anuncio()));
    	when(repositorioAnuncio.saveAndFlush(anuncio)).thenReturn(anuncio);
    	
    	boolean resultado;
    	resultado = anuncioService.actualizarAnuncio(anuncio);
    	
    	assertTrue(resultado);
    }
    
    @Test
    void siInvocoActualizarAnuncioYElAnuncioNoExistiaRetornoFalso() {
    	Anuncio anuncio = getAnuncio();
    	when(recepcionistaService.buscarRecepcionistaPorId(anyInt())).thenReturn(Optional.of(new Recepcionista()));
    	when(repositorioAnuncio.findById(anyInt())).thenReturn(Optional.empty());
    	
    	boolean resultado;
    	resultado = anuncioService.actualizarAnuncio(anuncio);
    	
    	assertFalse(resultado);
    }
    @Test
    @DisplayName("borrarAnuncioPorIdTEST1")
    void borrarAnuncioPorId() {
    }

    
    private Anuncio getAnuncio() {
    	Anuncio anuncio = new Anuncio();
    	anuncio.setInformacion("Informacion de anuncio");
    	anuncio.setRecepcionista(getRecepcionista());
    	return anuncio;
    }
    
    private Recepcionista getRecepcionista() {
    	Recepcionista recepcionista = new Recepcionista();
    	recepcionista.setId_recepcionista(1);
    	return recepcionista;
    }
}