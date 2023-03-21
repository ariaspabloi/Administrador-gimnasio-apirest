package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Contrato;
import com.example.demo.model.Metodo_Pago;
import com.example.demo.model.Recepcionista;
import com.example.demo.model.Suscripcion;
import com.example.demo.repository.RepositorioCliente;
import com.example.demo.repository.RepositorioRecepcionista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private RepositorioCliente repositorioCliente;

    @Mock
    private RecepcionistaServiceImpl recepcionistaService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    


    @Test
    public void buscarTodosLosClientes() {
        //Arrange
        List<Cliente> clientes = getClientes();
        when(repositorioCliente.findAll()).thenReturn(clientes);

        //Act
        List<Cliente> resultado = clienteService.buscarTodosLosClientes();

        //Assert
        assertNotNull(resultado);
        assertEquals(clientes.get(0),resultado.get(0));
    }

    @Test
    public void siTratoDeBorrarUsuarioConContratoNoSeBorrarYRetorna2(){
    	Cliente cliente = getCliente();
    	List<Contrato> contratos = new ArrayList<Contrato>();
    	contratos.add(getContratoHoy());
    	cliente.setContratos(contratos);
    	when(repositorioCliente.findById(anyInt())).thenReturn(Optional.of(cliente));

    	int resultado;
    	resultado = clienteService.borrarClientePorId(cliente.getId_cliente());
    	
    	assertEquals(2, resultado);
    }

    
    @Test
    public void cuandoRegistroDosUsuariosConElMismoRutElSistemaDebeRetornarFalso(){
        //Arrange
        Cliente clienteTest = getCliente();
        Optional<Recepcionista> r = Optional.of(clienteTest.getRecepcionista());
        Optional<Cliente> c = Optional.of(clienteTest);
        when(recepcionistaService.buscarRecepcionistaPorId(1)).thenReturn(r);
        when(repositorioCliente.findClienteByRutCliente(clienteTest.getRutCliente()))
                .thenReturn(c);

        //Act
        boolean resultado = clienteService.saveCliente(clienteTest);

        //Assert
        assertTrue(!resultado);
    }

    @Test
    public void TestsDeGetsDelCliente(){
        Cliente cliente = getCliente();
        Contrato contrato = cliente.getLastContrato();
        int id = cliente.getId_cliente();
        String rut = cliente.getRutCliente();
        int deuda = cliente.getDeuda();
        Recepcionista recepcionista = cliente.getRecepcionista();
        String direccion = cliente.getDireccion();
        String nombre = cliente.getNombre();
        String telefono = cliente.getTelefono();

        assertEquals(getContrato().getId_contrato(), contrato.getId_contrato());
        assertEquals(id, 1);
        assertEquals(rut, "20079145-2");
        assertEquals(deuda, 0);
        assertEquals(recepcionista.getId_recepcionista(), getRecepcionista().getId_recepcionista());
        assertEquals(direccion, "La Cabaña 122");
        assertEquals(nombre, "Pablo");
        assertEquals(telefono, "945458080");
    }


    @Test
    void saveClienteNuevoDebeRetornarTrue() {
        //Arrange
        Cliente clienteTest = getCliente();
        Optional<Recepcionista> r = Optional.of(clienteTest.getRecepcionista());
        Optional<Cliente> c = Optional.ofNullable(null);
        when(recepcionistaService.buscarRecepcionistaPorId(1)).thenReturn(r);
        when(repositorioCliente.findClienteByRutCliente(clienteTest.getRutCliente()))
                .thenReturn(c);
        when((repositorioCliente.saveAndFlush(clienteTest))).thenReturn(clienteTest);

        //Act
        boolean resultado = clienteService.saveCliente(clienteTest);

        //Assert
        assertTrue(resultado);

    }

    private Cliente getCliente(){

        Cliente cliente;
        cliente = new Cliente();
        cliente.setId_cliente(1);
        cliente.setRutCliente("20079145-2");
        cliente.setNombre("Pablo");
        cliente.setTelefono("945458080");
        cliente.setDeuda(0);
        cliente.setDireccion("La Cabaña 122");
        cliente.setContratos(Arrays.asList(getContrato()));
        cliente.setRecepcionista(getRecepcionista());
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

    private Recepcionista getRecepcionista(){

        Recepcionista recepcionista = new Recepcionista();
        recepcionista.setId_recepcionista(1);
        recepcionista.setRutRecepcionista("22.222.222-2");
        recepcionista.setHorarioLaboral("de 1 a 8");
        return recepcionista;
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

    private List<Cliente> getClientes(){

        List<Cliente> clientes = new ArrayList<>();

        Cliente cliente;
        cliente = new Cliente();
        cliente.setId_cliente(1);
        cliente.setRutCliente("11.111.111-1");
        cliente.setDeuda(10000);
        cliente.setDireccion("calle 2");
        cliente.setNombre("Señor Cliente");
        cliente.setTelefono("222222222");
        cliente.setRecepcionista(getRecepcionista());
        clientes.add(cliente);

        cliente = new Cliente();
        cliente.setId_cliente(3);
        cliente.setNombre("Juan");
        cliente.setRutCliente("33.333.333-3");
        cliente.setTelefono("33333333");
        cliente.setDireccion("calle 3");
        cliente.setRecepcionista(getRecepcionista());
        cliente.setDeuda(333);
        clientes.add(cliente);
        return clientes;
    }
    
    private Contrato getContratoHoy() {
    	Contrato contrato = new Contrato();
    	contrato.setFecha(LocalDate.now());
    	Suscripcion suscripcion = new Suscripcion();
    	suscripcion.setDuracion(30);
    	contrato.setSuscripcion(suscripcion);
    	return contrato;
    }
}