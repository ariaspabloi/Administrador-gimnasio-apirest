package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.demo.model.Taller;
import com.example.demo.repository.RepositorioTaller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TallerServiceImplTest {

    @Mock
    RepositorioTaller repTaller;

    @InjectMocks
    private TallerServiceImpl tallerService;

    @Test
    void siConsultoTalleresMasAsistidosYNoHayAsistenciasEntoncesSistemaArrojaResultadoVacio(){
        // Arrange
        List<Taller> lista = new ArrayList<>();
        Taller taller3 = getTaller2();
        taller3.setId_taller(3);

        lista.add(getTaller());
        lista.add(getTaller2());
        lista.add(taller3);
        when(repTaller.findAll()).thenReturn(lista);

        // Act
        List<Taller> masAsistidos = tallerService.reporteMasAsistidos();

        // Assert
        assertTrue(masAsistidos.isEmpty());
    }


    @Test
    void siConsultoHorarioDeTalleresCuandoExistenEntoncesRetornaInformacionSolicitada(){
        // Arrange
        List<Taller> lista = new ArrayList<>();
        lista.add(getTaller());
        lista.add(getTaller2());
        when(repTaller.findAll()).thenReturn(lista);

        // Act
        List<Taller> resultado = tallerService.buscarTodosLosTalleres();

        // Assert
        assertTrue(resultado.get(0).getId_taller() == getTaller().getId_taller());
        assertTrue(resultado.get(0).getNombre().equals(getTaller().getNombre()));
        assertTrue(resultado.get(0).getHorario().equals(getTaller().getHorario()));
        assertTrue(resultado.get(1).getId_taller() == getTaller2().getId_taller());
        assertTrue(resultado.get(1).getNombre().equals(getTaller2().getNombre()));
        assertTrue(resultado.get(1).getHorario().equals(getTaller2().getHorario()));

    }

    @Test
    void siConsultoHorarioDeTalleresCuandoNoExistenEntoncesNoRetornaInformacion(){
        // Arrange
        List<Taller> lista = new ArrayList<>();
        when(repTaller.findAll()).thenReturn(lista);

        // Act
        List<Taller> resultado = tallerService.buscarTodosLosTalleres();

        // Assert
        assertTrue(resultado.isEmpty());
    }

    private Taller getTaller(){
        Taller taller = new Taller();
        taller.setId_taller(1);
        taller.setCupos(999);
        taller.setHorario("de 1 a 2");
        taller.setNombre("Tallarinata bailable");

        return taller;
    }
    private Taller getTaller2(){
        Taller taller = new Taller();
        taller.setId_taller(2);
        taller.setCupos(1);
        taller.setHorario("15:00 - 15:30");
        taller.setNombre("Contemplar la soledad");

        return taller;
    }
}
