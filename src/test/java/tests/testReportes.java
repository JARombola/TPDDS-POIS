package tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import externos.BufferBusquedas;
import externos.BuscadorCGPExterno;
import externos.OrigenDatos;
import principal.HistorialBusqueda;
import principal.Buscador;
import principal.ControlTerminales;
import principal.Mapa;
import principal.Terminal;
import tipos.ParadaColectivo;

public class testReportes {
	Terminal terminal,terminal2;
	HistorialBusqueda busqueda1,busqueda2,busqueda3,busqueda4;
	LocalDate fecha1, fecha2, fecha3, fecha4;
	List<HistorialBusqueda> almacenamientoBusquedas;
	Mapa mapa;
	ParadaColectivo parada1, parada2;
	ControlTerminales controlMaestro;
	BufferBusquedas buffer;
	OrigenDatos cgpExterno;
	BuscadorCGPExterno buscadorCgp;
	Buscador buscador;
	
	
	
	@Before
	public void initialize(){
		mapa=new Mapa();
		buffer=new BufferBusquedas();
		cgpExterno=Mockito.mock(OrigenDatos.class);
		buscadorCgp=new BuscadorCGPExterno();
		buscadorCgp.setComponente(cgpExterno);
		controlMaestro=new ControlTerminales();
		parada1 = new ParadaColectivo();
		parada2 = new ParadaColectivo();
		parada1.setNombre("primer parada de la linea 114");
		parada2.setNombre("segunda parada de la linea 114");
		fecha1 = new LocalDate(2016,02,01);
		fecha2 = new LocalDate(2016,02,02);
		fecha3 = new LocalDate(2016,02,02);
		fecha4 = new LocalDate(2016,12,23);
		busqueda1 = new HistorialBusqueda();
		busqueda1.setFecha(fecha1);
		busqueda1.setFraseBuscada("Perro");
		busqueda1.setCantidadResultados(100);
		busqueda2 = new HistorialBusqueda();
		busqueda2.setFecha(fecha2);
		busqueda2.setFraseBuscada("Gato");
		busqueda2.setCantidadResultados(200);
		busqueda3 = new HistorialBusqueda();
		busqueda3.setFecha(fecha3);
		busqueda3.setFraseBuscada("Operativos");
		busqueda3.setCantidadResultados(2);
		busqueda4 = new HistorialBusqueda();
		busqueda4.setFecha(fecha4);
		busqueda4.setFraseBuscada("Dejar_la_facultad");
		almacenamientoBusquedas = new ArrayList<HistorialBusqueda>();
		mapa.setPOI(parada1);
		mapa.setPOI(parada2);
		terminal = new Terminal();
		terminal2=new Terminal();
		terminal.setHistorialBusquedas(almacenamientoBusquedas);
		terminal.setMapa(mapa);
		terminal.setNombre("Terminal 1");
		terminal2.setHistorialBusquedas(almacenamientoBusquedas);
		terminal2.setMapa(mapa);
		terminal2.setNombre("Terminal 2");
		controlMaestro.agregarTerminal(terminal);
		controlMaestro.agregarTerminal(terminal2);
		buscador = new Buscador();
		terminal.setBuscador(buscador);
		buscador.setMapa(mapa);
		buscador.setBuffer(buffer);
	}
	
	@Test
	public void testReportePorFecha (){
		almacenamientoBusquedas.add(busqueda1);
		almacenamientoBusquedas.add(busqueda2);
		almacenamientoBusquedas.add(busqueda3);
		almacenamientoBusquedas.add(busqueda4);
		assertEquals(terminal.reporteFechas(),4,0);
	}
	
	@Test
	public void testReporteAlRealizarBusquedas (){
		terminal.buscar("Hola", "Chau");
		terminal.buscar("114", "");
		terminal.buscar("Julian", "Crack");
		terminal.reporteFechas();
		assertEquals(terminal.cantidadTotalResultados(),2,0);			//hubieron 2 aciertos (114)
		terminal.buscar("114", "");
		assertEquals(terminal.cantidadTotalResultados(),4,0);			//2 aciertos mas
	}
	@Test
	public void testReportesParcialesTerminal1(){
		almacenamientoBusquedas.add(busqueda1);		//100 resultados
		almacenamientoBusquedas.add(busqueda2);		//200
		almacenamientoBusquedas.add(busqueda3);		//2
		almacenamientoBusquedas.add(busqueda4);		//0
		int cantidadBusquedas=controlMaestro.busquedasParcialesPorTerminal(terminal);
		assertEquals(cantidadBusquedas, 4);
	}
	@Test
	public void testBusquedasTotales(){
		almacenamientoBusquedas.add(busqueda1);		//100 resultados
		almacenamientoBusquedas.add(busqueda2);		//200
		almacenamientoBusquedas.add(busqueda3);		//2
		almacenamientoBusquedas.add(busqueda4);	
		int totalResultados= controlMaestro.busquedasTotalesDeTerminales();
		assertEquals(totalResultados, 604,0);				//302 de cada terminal (2) = 604
	}
	
	
	@Test
	public void tiempoBusqueda(){			//no funciona, no se como hacer que el mock "espere"...
		 Mockito.when(cgpExterno.buscar("asd","asd")).thenReturn("asd");
		cgpExterno.buscar("asd","asd");
		  Mockito.verify(cgpExterno,Mockito.timeout(100)).buscar("asd","asd");
	}
	
}



