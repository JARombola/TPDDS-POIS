package tests;


import java.util.List;

import org.hibernate.event.spi.ClearEvent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import externos.BuscadorBancoExterno;
import externos.BuscadorCGPExterno;
import externos.OrigenDatos;
import pois.POI;
import terminales.BufferBusquedas;
import terminales.Busqueda;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;
import tiposPoi.Rubro;
import tiposPoi.Servicio;

public class TestBusqueda extends AbstractPersistenceTest implements WithGlobalEntityManager {
	int encontrados;
	private ParadaColectivo parada1, parada2, parada3;
	private Rubro muebleria;
	private Local mueblesSA,mueblesParaTodos;
	private Servicio asesoramiento,jubilacion,asesoramiento2;
	private CGP cgp;
	private Banco banco;
	private Mapa mapa;
	private BufferBusquedas buffer;
	private BuscadorBancoExterno buscadorBanco;
	private BuscadorCGPExterno buscadorCgp;
	private OrigenDatos origenBanco,origenCGP;
	private Terminal terminal;

	@After
	public void eliminarPois(){
		List<POI> p = createQuery("from POI").getResultList();
		p.stream().forEach(e->mapa.eliminarPOI(e.getId()));
		p = createQuery("from POI").getResultList();
	//	System.out.println(p.size());
	}
	
	@Before
	public void initialize(){
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
		 asesoramiento2= new Servicio("asesoramiento");
		jubilacion = new Servicio("jubilacion");
			cgp = new CGP();
			banco = new Banco();
				cgp.agregarServicio(asesoramiento);
				cgp.setNombre("CGP nro 1");
				banco.agregarServicio(asesoramiento2);
				banco.agregarServicio(jubilacion);
				banco.setNombre("Banco Nacion");
				
		mapa = new Mapa();
			mapa.agregarOmodificar(parada1);
			mapa.agregarOmodificar(parada2);
			mapa.agregarOmodificar(parada3);
			mapa.agregarOmodificar(mueblesSA);
			mapa.agregarOmodificar(mueblesParaTodos);
			mapa.agregarOmodificar(cgp);
			mapa.agregarOmodificar(banco);
			
		buffer=new BufferBusquedas();
			buffer.agregarExterno(buscadorBanco);
			buffer.agregarExterno(buscadorCgp);
		terminal = new Terminal();
			terminal.setBuffer(buffer);
			terminal.setMapa(mapa);
		
	}
	

	@Test		
	public void busquedaParadas114(){
		encontrados=terminal.buscar("114","").size();		//3 paradas
		Assert.assertEquals(encontrados, 3,0);
		Mockito.verify(origenCGP,Mockito.times(1)).buscar("114");		//a origen1 no lo llama xq corresponde a un banco
		buffer.borrarBusquedaCache("114");
	}
	
	@SuppressWarnings("unchecked")
	//@Test	
	public void busquedaAsesoramiento(){
		
		List<CGP> b = (List<CGP>) createQuery("from POI p where nombre='CGP nro 1'").getResultList();
		System.out.println(b.get(0).getListaServicios().getServicios().get(0).getTags().get(0));
	
		List<POI> a = (List<POI>) createQuery("from POI p where 'asesoramiento' MEMBER OF p.listaServicios.servicios.tags").getResultList();
		a.stream().forEach(p->System.out.println("<<<"+p.getNombre()));
		System.out.println(
		
		terminal.buscar("asesoramiento","").get(0).getNombre());
		encontrados=terminal.buscar("asesoramiento","").size();
		Assert.assertEquals(encontrados,2,0);					//banco y CGP	
	}
	
	@Test	
	public void busquedaJubilacion(){
		encontrados=terminal.buscar("jubilacion","").size();		//banco
		Assert.assertEquals(encontrados,1,0);
		Mockito.verify(origenCGP,Mockito.times(1)).buscar("jubilacion");	
		buffer.borrarBusquedaCache("jubilacion");
	}
	
	@Test	
	public void busquedaSociedad(){
		encontrados=terminal.buscar("sociedad","").size();		//muebles sociedad anonima=1	
		Assert.assertEquals(encontrados,1,0);
	}
	
	@Test
	public void busquedaExternosCGP(){
		terminal.buscar("asesoramiento","");
		Mockito.verify(origenCGP,Mockito.times(1)).buscar("asesoramiento");
		buffer.borrarBusquedaCache("asesoramiento");
	}
	
	
}
