package principal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Rubro;
import tipos.Servicio;


public class testBusqueda {
	
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
		muebleria = new Rubro();
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
				banco.setNombre("Nacion");
		mapa = new Mapa();
			mapa.agregarPOI(parada1);
			mapa.agregarPOI(parada2);
			mapa.agregarPOI(parada3);
			mapa.agregarPOI(mueblesSA);
			mapa.agregarPOI(mueblesParaTodos);
			mapa.agregarPOI(cgp);
			mapa.agregarPOI(banco);
			
	}
	@Test		
	public void busquedaParadas114(){
		mapa.Buscar("114");
	}
	@Test	
	public void busquedaAsesoramiento(){
		mapa.Buscar("asesoramiento");
	}
	@Test	
	public void busquedaJubilacion(){
		mapa.Buscar("jubilacion");
	}
	@Test	
	public void busquedaSociedad(){
		mapa.Buscar("sociedad");
	}
}
