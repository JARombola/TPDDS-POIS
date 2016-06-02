package tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import principal.Busqueda;
import principal.Mapa;
import principal.Terminal;
import tipos.ParadaColectivo;

public class testReportes {
	Terminal terminal;
	Busqueda busqueda1,busqueda2,busqueda3,busqueda4;
	LocalDate fecha1, fecha2, fecha3, fecha4;
	List<Busqueda> almacenamientoBusquedas;
	Mapa mapa;
	ParadaColectivo parada1, parada2;
	
	@Before
	public void initialize(){
		mapa=new Mapa();
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
		busqueda2 = new Busqueda();
		busqueda2.setFecha(fecha2);
		busqueda2.setFraseBuscada("Gato");
		busqueda3 = new Busqueda();
		busqueda3.setFecha(fecha3);
		busqueda3.setFraseBuscada("Operativos");
		busqueda4 = new Busqueda();
		busqueda4.setFecha(fecha4);
		busqueda4.setFraseBuscada("Dejar_la_facultad");
		
		almacenamientoBusquedas = new ArrayList<Busqueda>();
		mapa.setPOI(parada1);
		mapa.setPOI(parada2);
		terminal = new Terminal();
		terminal.setHistorialBusquedas(almacenamientoBusquedas);
		terminal.setMapa(mapa);
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
}



