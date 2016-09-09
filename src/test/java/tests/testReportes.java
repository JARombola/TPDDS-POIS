package tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import configuracionTerminales.FuncionesExtra;
import externos.BuscadorCGPExterno;
import terminales.BufferBusquedas;
import terminales.Busqueda;
import terminales.ControlTerminales;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.ParadaColectivo;

public class testReportes {
	Terminal terminal,terminal2;
	Busqueda busqueda1,busqueda2,busqueda3,busqueda4;
	LocalDate fecha1, fecha2, fecha3, fecha4;
	List<Busqueda> almacenamientoBusquedas;
	Mapa mapa;
	ParadaColectivo parada1, parada2;
	ControlTerminales controlMaestro;
	BufferBusquedas buffer;
	BuscadorCGPExterno buscadorCgp;
	FuncionesExtra extra;
	
	
	@Before
	public void initialize(){
		mapa=new Mapa();
		buffer=new BufferBusquedas();
		controlMaestro=new ControlTerminales();
		parada1 = new ParadaColectivo();
		parada2 = new ParadaColectivo();
		parada1.setNombre("primer parada de la linea 114");
		parada2.setNombre("segunda parada de la linea 114");
		fecha1 = new LocalDate(2016,02,01);
		fecha2 = new LocalDate(2016,02,02);
		fecha3 = new LocalDate(2016,02,02);
		fecha4 = new LocalDate(2016,12,23);
		busqueda1 = new Busqueda();
		busqueda1.setFecha(fecha1);
		busqueda1.setFraseBuscada("Perro");
		busqueda1.setCantidadResultados(100);
		busqueda2 = new Busqueda();
		busqueda2.setFecha(fecha2);
		busqueda2.setFraseBuscada("Gato");
		busqueda2.setCantidadResultados(200);
		busqueda3 = new Busqueda();
		busqueda3.setFecha(fecha3);
		busqueda3.setFraseBuscada("Operativos");
		busqueda3.setCantidadResultados(2);
		busqueda4 = new Busqueda();
		busqueda4.setFecha(fecha4);
		busqueda4.setFraseBuscada("Dejar_la_facultad");
		almacenamientoBusquedas = new ArrayList<Busqueda>();
		
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
		
		extra= new FuncionesExtra(10);

		terminal.setBuffer(buffer);
		terminal2.setBuffer(buffer);
		terminal.setExtra(extra);
		terminal2.setExtra(extra);
		controlMaestro.agregarTerminal(terminal);
		controlMaestro.agregarTerminal(terminal2);
	}
	
	@Test
	public void testReportePorFecha (){
		almacenamientoBusquedas.add(busqueda1);
		almacenamientoBusquedas.add(busqueda2);
		almacenamientoBusquedas.add(busqueda3);
		almacenamientoBusquedas.add(busqueda4);
		assertEquals(terminal.reporteFechas().size(),3,0);			//4 busquedas, pero 2 son del mismo dia => cuentan como una
	}
	
	@Test
	public void testOpcionBusqueda(){
		extra.setTerminal(terminal);
		
		terminal.iniciarBusqueda("Hola", "Chau");
		terminal.iniciarBusqueda("114", "");
		terminal.iniciarBusqueda("Julian", "Crack");
		assertEquals(terminal.cantidadTotalResultados().getDatos(),0,0);			//No se registraron, estaba desactivado
		terminal.activarOpcion("historial");
		terminal.iniciarBusqueda("Hola", "Chau");
		terminal.iniciarBusqueda("114", "");
		terminal.iniciarBusqueda("Julian", "Crack");
		assertEquals(terminal.cantidadTotalResultados().getDatos(),2,0);			//Registrados, hubieron 2 aciertos (114)
		terminal.iniciarBusqueda("114", "");
		assertEquals(terminal.cantidadTotalResultados().getDatos(),4,0);			//2 aciertos mas
		terminal.desactivarOpcion("historial");
		terminal.iniciarBusqueda("114", "");
		assertEquals(terminal.cantidadTotalResultados().getDatos(),4,0);
	}
	
	@Test
	public void testReportesParcialesTerminal1(){
		almacenamientoBusquedas.add(busqueda1);		//100 resultados
		almacenamientoBusquedas.add(busqueda2);		//200
		almacenamientoBusquedas.add(busqueda3);		//2
		almacenamientoBusquedas.add(busqueda4);		//0
		int cantidadBusquedas=controlMaestro.busquedasParcialesPorTerminal(terminal).size();
		assertEquals(cantidadBusquedas, 4);
	}
	
	
}



