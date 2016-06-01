package tests;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import principal.Busqueda;
import principal.Terminal;

public class testReportes {
	Terminal terminal;
	Busqueda busqueda1;
	Busqueda busqueda2;
	Busqueda busqueda3;
	Busqueda busqueda4;
	Date fecha1;
	Date fecha2;
	Date fecha3;
	Date fecha4;
	SimpleDateFormat dateFormat;
	List<Busqueda> almacenamientoBusquedas;
	
	
	@Before
	public void initialize(){
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		fecha1 = new Date();
		try{
			fecha1 = dateFormat.parse("01/02/2016");
		}catch(ParseException ex){}
		
		fecha2 = new Date();
		try{
			fecha2 = dateFormat.parse("02/02/2016");
		}catch(ParseException ex){}
		
		fecha3 = new Date();
		try{
			fecha3 = dateFormat.parse("02/02/2016");
		}catch(ParseException ex){}
		
		fecha4 = new Date();
		try{
			fecha4 = dateFormat.parse("23/12/2016");
		}catch(ParseException ex){}
		
		busqueda1 = new Busqueda();
		busqueda1.setFecha(fecha1);
		busqueda2 = new Busqueda();
		busqueda2.setFecha(fecha2);
		busqueda3 = new Busqueda();
		busqueda3.setFecha(fecha3);
		busqueda4 = new Busqueda();
		busqueda4.setFecha(fecha4);
		
		almacenamientoBusquedas = new ArrayList();
		almacenamientoBusquedas.add(busqueda1);
		almacenamientoBusquedas.add(busqueda2);
		almacenamientoBusquedas.add(busqueda3);
		almacenamientoBusquedas.add(busqueda4);
		
		terminal = new Terminal();
		terminal.setAlmacenamientoBusquedas(almacenamientoBusquedas);
		
	}
	
	@Test
	public void testReportePorFecha (){
		terminal.reporteFechas();
	}
}



