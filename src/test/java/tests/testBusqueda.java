package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import principal.Mapa;
import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Rubro;
import tipos.Servicio;


public class testBusqueda {
	int encontrados;
	ParadaColectivo parada1;
	ParadaColectivo parada2;
	ParadaColectivo parada3;
	Rubro muebleria;
	Local mueblesSA;
	Local mueblesParaTodos;
	Servicio asesoramiento;
	Servicio jubilacion;
	CGP cgp;
	Banco banco;
	Mapa mapa;
	
	@Before
	public void initialize(){
		parada1 = new ParadaColectivo();
		parada2 = new ParadaColectivo();
		parada3 = new ParadaColectivo();
			parada1.setNombre("primer parada de la linea 114");
			parada2.setNombre("segunda parada de la linea 114");
			parada3.setNombre("tercera parada de la linea 114");
			parada3.agregarTag("Lento");
			parada3.agregarTag("Llegas tarde");
			parada3.agregarTag("Feo");
		muebleria = new Rubro("muebleria");
			mueblesSA = new Local();
			mueblesParaTodos = new Local();
				mueblesSA.setRubro(muebleria);
				mueblesSA.setNombre("muebles sociedad anonima");
				mueblesParaTodos.setRubro(muebleria);
				mueblesParaTodos.setNombre("otra muebleria");
		asesoramiento = new Servicio("asesoramiento");
		jubilacion = new Servicio("jubilacion");
			cgp = new CGP();
			banco = new Banco();
				cgp.agregarServicio(asesoramiento);
				cgp.setNombre("CGP nro 1");
				banco.agregarServicio(asesoramiento);
				banco.agregarServicio(jubilacion);
				banco.setNombre("Banco Nacion");
		mapa = new Mapa();
			mapa.setPOI(parada1);
			mapa.setPOI(parada2);
			mapa.setPOI(parada3);
			mapa.setPOI(mueblesSA);
			mapa.setPOI(mueblesParaTodos);
			mapa.setPOI(cgp);
			mapa.setPOI(banco);
			
	}

	@Test		
	public void busquedaParadas114(){
		encontrados=mapa.Buscar("114").size();		//3 paradas
		assertEquals(encontrados, 3);
	}
	@Test	
	public void busquedaAsesoramiento(){
		encontrados=mapa.Buscar("asesoramiento").size();
		assertEquals(encontrados,2);					//banco y CGP
		
	}
	@Test	
	public void busquedaJubilacion(){
		encontrados=mapa.Buscar("jubilacion").size();		//banco
		assertEquals(encontrados,1);
	}
	@Test	
	public void busquedaSociedad(){
		encontrados=mapa.Buscar("sociedad").size();		//muebles sociedad anonima=1	
		assertEquals(encontrados,1);
	}
	@Test
	public void busquedaTagsParada(){
		encontrados=mapa.Buscar("Feo").size();
		assertEquals(encontrados,1);
	}
}
