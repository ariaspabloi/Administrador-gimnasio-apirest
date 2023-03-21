package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Recepcionista;
import com.example.demo.repository.RepositorioRecepcionista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecepcionistaServiceImplTest {

    @Mock
    private RepositorioRecepcionista repositorioRecepcionista;

    @InjectMocks
    private RecepcionistaServiceImpl recepcionistaService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void buscarTodosLosRecepcionistas() {
        List<Recepcionista> recepcionistas = getRecepcionistas();
        when(repositorioRecepcionista.findAll()).thenReturn(recepcionistas);

        List<Recepcionista> resultado = recepcionistaService.buscarTodosLosRecepcionistas();

        assertNotNull(resultado);
        assertEquals(recepcionistas.get(0),resultado.get(0));
    }

    @Test
    public void SiInvocoBuscarRecepcionistasYNoExisteNingunoRetornaVacio(){

        List<Recepcionista> recepcionistas = recepcionistaService.buscarTodosLosRecepcionistas();

        assertTrue(recepcionistas.isEmpty());

    }


    private List<Recepcionista> getRecepcionistas(){

        List<Recepcionista> recepcionistas = new ArrayList<>();

        Recepcionista recepcionista = new Recepcionista();
        recepcionista.setId_recepcionista(1);
        recepcionista.setRutRecepcionista("11.111.111-1");
        recepcionista.setHorarioLaboral("de 1 a 8");
        recepcionistas.add(recepcionista);

        recepcionista = new Recepcionista();
        recepcionista.setId_recepcionista(2);
        recepcionista.setRutRecepcionista("22.222.222-2");
        recepcionista.setHorarioLaboral("de 8 a 16");
        recepcionistas.add(recepcionista);
        return recepcionistas;

    }
}