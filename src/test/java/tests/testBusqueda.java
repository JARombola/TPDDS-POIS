package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import externos.BufferBusquedas;
import externos.BuscadorBancoExterno;
import externos.BuscadorCGPExterno;
import externos.OrigenDatos;
import principal.Mapa;
import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Rubro;
import tipos.Servicio;


public class testBusqueda {
	int encontrados;
	ParadaColectivo parada1, parada2, parada3;
	Rubro muebleria;
	Local mueblesSA,mueblesParaTodos;
	Servicio asesoramiento,jubilacion;
	CGP cgp;
	Banco banco;
	Mapa mapa;
	BufferBusquedas buffer;
	BuscadorBancoExterno buscadorBanco;
	BuscadorCGPExterno buscadorCgp;
	OrigenDatos origenBanco,origenCGP,origen3;

	
	@Before
	public void initialize(){
		buffer=new BufferBusquedas();
		buscadorBanco=new BuscadorBancoExterno();
		buscadorCgp=new BuscadorCGPExterno();
		origenBanco =Mockito.mock(OrigenDatos.class);
		buscadorBanco.setComponente(origenBanco);
		origenCGP =Mockito.mock(OrigenDatos.class);
		buscadorCgp.setComponente(origenCGP);
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
			buffer.agregarExterno(buscadorBanco);
			buffer.agregarExterno(buscadorCgp);
			mapa.setBuffer(buffer);
	}

	@Test		
	public void busquedaParadas114(){
		encontrados=mapa.buscar("114","").size();		//3 paradas
		Assert.assertEquals(encontrados, 3,0);
		Mockito.verify(origenCGP,Mockito.times(1)).buscar("114");		//a origen1 no lo llama xq corresponde a un banco
	}
	
	@Test	
	public void busquedaAsesoramiento(){
		encontrados=mapa.buscar("asesoramiento","").size();
		Assert.assertEquals(encontrados,2,0);					//banco y CGP	
	}
	
	@Test	
	public void busquedaJubilacion(){
		encontrados=mapa.buscar("jubilacion","").size();		//banco
		Assert.assertEquals(encontrados,1,0);
		Mockito.verify(origenCGP,Mockito.times(1)).buscar("jubilacion");	
	}
	
	@Test	
	public void busquedaSociedad(){
		encontrados=mapa.buscar("sociedad","").size();		//muebles sociedad anonima=1	
		Assert.assertEquals(encontrados,1,0);
	}
	
	@Test
	public void busquedaExternosCGP(){
		mapa.buscar("asesoramiento","");
		Mockito.verify(origenCGP,Mockito.times(1)).buscar("asesoramiento");
	}
}
