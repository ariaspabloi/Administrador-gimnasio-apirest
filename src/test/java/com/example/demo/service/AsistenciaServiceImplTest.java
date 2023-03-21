package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.repository.*;

@ExtendWith(MockitoExtension.class)
class AsistenciaServiceImplTest {

    @Mock
    private RepositorioAsistencia repAsistencia;

    @Mock
    private RecepcionistaService recepcionistaService;

    @Mock
    private ClienteService clienteService;
    
    @InjectMocks
    private AsistenciaServiceImpl asistenciaService;

    @Test
    void siInvocoAsistenciasSinNingunParametroDebeRetornarTodasLasAsistenciasRegistradasEnLaBaseDeDatos() {
    	//Arrange
        List<Asistencia> asistencias = getAsistencias();
        when(repAsistencia.findAll()).thenReturn(asistencias);

        //Act
        List<Asistencia> resultado = asistenciaService.buscarTodasLasAsistencias();

        //Assert
        assertNotNull(resultado);
        assertEquals(asistencias.size(),resultado.size());
        for(int i = 0; i<resultado.size(); i++)
        	assertEquals(asistencias.get(i),resultado.get(i));

    }

    @Test
    void siInvocoAsistenciasYPidoReporteEntoncesDebeRetornarElReporteDeTodasLasAsistenciasDelDiaDeHoy() {
    	//Arrange
    	List<Asistencia> asistencias = getAsistencias();
        when(repAsistencia.findAll()).thenReturn(asistencias);
        
        //Act
        List<Asistencia> resultado = asistenciaService.todayAsistencias();
        
        //Assert
        assertNotNull(resultado);
        assertEquals(resultado.size(), 4);
        for(int i=0; i<4; i++)
        	assertTrue(resultado.get(i).getFecha_entrada().toLocalDate().equals(LocalDate.now()));
    }


    @Test
    void siInvocoAsistenciasYPidoReporteDeUnDiaEnEspecificoDebeRetornarElReporteDeTodasLasAsistenciasDeEseDia() {
    	//Arrange
    	List<Asistencia> asistencias = getAsistencias();
        when(repAsistencia.findAll()).thenReturn(asistencias);
        LocalDate fecha = LocalDate.of(2022,11,30);
        
        //Act
        List<Asistencia> resultado = asistenciaService.reporteDia(fecha);
        
        //Assert
        assertNotNull(resultado);
        assertEquals(resultado.size(), 1);
        assertTrue(resultado.get(0).getFecha_entrada().toLocalDate().equals(fecha));
    }
    
    @Test
    void siInvocoAsisteciasSegunUnRangoDeFechasObtengoTantosReportesComoFechasConSusRespectivasAsistenciasTotalesPorDia() {
    	//Arrange
    	List<Asistencia> asistencias = getAsistencias();
        when(repAsistencia.findAll()).thenReturn(asistencias);
        LocalDate inicio = LocalDate.of(2022,12,1);
        LocalDate fin = LocalDate.of(2022,12,4);
        
        //Act
        List<List<Asistencia>> resultado = asistenciaService.reporte(inicio,fin);
        
        //Assert
        assertNotNull(resultado);
        assertEquals(resultado.size(), 3); // 1-12, 2-12, 3-12
        int n = 0;
        for(LocalDate i = inicio; i.isBefore(fin); i=i.plusDays(1)) {
        	for(int j = 0; j < resultado.get(n).size(); j++)
        		assertTrue(resultado.get(n).get(j).getFecha_entrada().toLocalDate().equals(i));
        	n++;
        }    
    }

    @Test
    void siIntentoIngresarUnaAsistenciaDeUnClienteQueNoTieneUnContratoActivoEntoncesDebeRetornarFalse() {
        //Arrange
        Recepcionista recepcionista = getRecepcionista();
        Asistencia auxAsistencia = getUnaAsistencia(true);
        auxAsistencia.setRecepcionista(recepcionista);
        when(recepcionistaService.existById(1)).thenReturn(true);
        when(clienteService.tieneContratoByRut(auxAsistencia.getRut_cliente())).thenReturn(false);

        //Act
        boolean resultado = asistenciaService.saveAsistencia(auxAsistencia);

        //
        assertFalse(resultado);
    }

    @Test
    void siIntentoIngresarUnaAsistenciaDeUnClienteQueTieneUnContratoActivoEntoncesDebeRetornarTrue() {
        //Arrange
        Recepcionista recepcionista = getRecepcionista();
        Asistencia auxAsistencia = getUnaAsistencia(true);
        auxAsistencia.setRecepcionista(recepcionista);
        when(recepcionistaService.existById(1)).thenReturn(true);
        when(clienteService.tieneContratoByRut(auxAsistencia.getRut_cliente())).thenReturn(true);
        when(repAsistencia.saveAndFlush(auxAsistencia)).thenReturn(auxAsistencia);

        //Act
        boolean resultado = asistenciaService.saveAsistencia(auxAsistencia);

        //
        assertTrue(resultado);
    }

    @Test
    void siSeIntentaEliminarLaAsistenciaPasadoCincoMinutosDeQueElUsuarioIngresoAlGimnasioDebeRetornarDosYNoEliminarla() {
        //Arrange
        Asistencia miAsistencia = getUnaAsistencia(true);
        when(repAsistencia.existsById(10)).thenReturn(true);
        when(repAsistencia.findById(10)).thenReturn(Optional.of(miAsistencia));

        //Act
        int resultado = asistenciaService.deleteAsistencia(10);

        //Assert
        assertEquals(resultado,2);
    }

    @Test
    void siSeIntentaEliminarLaAsistenciaAntesDeCincoMinutosDeQueElUsuarioIngresoAlGimnasioDebeEliminar() {
        //Arrange
        Asistencia miAsistencia = getUnaAsistencia(false);
        when(repAsistencia.existsById(10)).thenReturn(true);
        when(repAsistencia.findById(10)).thenReturn(Optional.of(miAsistencia));

        //Act
        int resultado = asistenciaService.deleteAsistencia(10);

        //Assert
        assertEquals(resultado,1);
    }

    @Test
    void siSeIntentaEliminarLaAsistenciaDeUnUsuarioQueNoExisteDebeRetornar0() {
        //Arrange
        when(repAsistencia.existsById(10)).thenReturn(false);

        //Act
        int resultado = asistenciaService.deleteAsistencia(10);

        //Assert
        assertEquals(resultado,0);
    }


    private List<Asistencia> getAsistencias(){
    	Recepcionista r = new Recepcionista();
    	r.setHorarioLaboral("24");
    	r.setId_recepcionista(1);
    	r.setRutRecepcionista("1232");

        List<Asistencia> asistencias = new ArrayList<>();

        Asistencia asistencia = new Asistencia();
        asistencia.setId_asistencia(1);
        asistencia.setFecha_entrada(LocalDateTime.of(2022,11,30,13,13));
        asistencia.setFecha_salida(LocalDateTime.of(2022,12,1,13,13));
        asistencia.setRut_cliente("111111111");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);

        asistencia = new Asistencia();
        asistencia.setId_asistencia(2);
        asistencia.setFecha_entrada(LocalDateTime.of(2022,12,2,13,13));
        asistencia.setFecha_salida(LocalDateTime.of(2022,12,03,13,13));
        asistencia.setRut_cliente("222222222");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        asistencia = new Asistencia();
        
        asistencia.setId_asistencia(3);
        asistencia.setFecha_entrada(LocalDateTime.of(2022,12,3,13,13));
        asistencia.setFecha_salida(LocalDateTime.of(2022,12,03,13,13));
        asistencia.setRut_cliente("33333333333");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        
        asistencia = new Asistencia();
        asistencia.setId_asistencia(4);
        asistencia.setFecha_entrada(LocalDateTime.of(2022,12,3,13,13));
        asistencia.setFecha_salida(LocalDateTime.of(2022,12,03,13,13));
        asistencia.setRut_cliente("4444444444");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        
        asistencia = new Asistencia();
        asistencia.setId_asistencia(5);
        asistencia.setFecha_entrada(LocalDateTime.of(2022,12,3,13,13));
        asistencia.setFecha_salida(LocalDateTime.of(2022,12,03,13,13));
        asistencia.setRut_cliente("555555555");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        
        asistencia = new Asistencia();
        asistencia.setId_asistencia(6);
        asistencia.setFecha_entrada(LocalDateTime.now());
        asistencia.setFecha_salida(LocalDateTime.now());
        asistencia.setRut_cliente("66666666");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        
        asistencia = new Asistencia();
        asistencia.setId_asistencia(7);
        asistencia.setFecha_entrada(LocalDateTime.now());
        asistencia.setFecha_salida(LocalDateTime.now());
        asistencia.setRut_cliente("777777777");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        
        asistencia = new Asistencia();
        asistencia.setId_asistencia(8);
        asistencia.setFecha_entrada(LocalDateTime.now());
        asistencia.setFecha_salida(LocalDateTime.now());
        asistencia.setRut_cliente("88888888");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        
        asistencia = new Asistencia();
        asistencia.setId_asistencia(9);
        asistencia.setFecha_entrada(LocalDateTime.now());
        asistencia.setFecha_salida(LocalDateTime.now());
        asistencia.setRut_cliente("99999999999");
        asistencia.setRecepcionista(r);
        asistencias.add(asistencia);
        return asistencias;

    }

    private Asistencia getUnaAsistencia(boolean pasadoCincoMinutos){
        Asistencia myAsistencia = new Asistencia();
        myAsistencia.setId_asistencia(10);
        if(pasadoCincoMinutos)
            myAsistencia.setFecha_entrada(LocalDateTime.now().minusMinutes(6));
        else
            myAsistencia.setFecha_entrada(LocalDateTime.now());
        myAsistencia.setRut_cliente("24.500-03");
        return myAsistencia;
    }

    private Recepcionista getRecepcionista(){

        Recepcionista recepcionista = new Recepcionista();
        recepcionista.setId_recepcionista(1);
        recepcionista.setRutRecepcionista("22.222.222-2");
        recepcionista.setHorarioLaboral("de 1 a 8");
        return recepcionista;
    }
}