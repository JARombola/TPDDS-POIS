package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import pois.Comuna;
import pois.Direccion;
import pois.POI;
import terminales.BufferBusquedas;
import terminales.Busqueda;
import terminales.LocalDateConverter;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;
import tiposPoi.Rubro;
import tiposPoi.Servicio;

public class testMongo {			
			
			//NO FUNCIONA SI NO EST� MONGO EJECUTANDO, SORRY!!
	
	private MongoClient mongo;
	private Morphia morphia;
	private Datastore store;
	
	@Before								
	public void init() {					
		morphia = new Morphia();
		mongo = new MongoClient();
		morphia.mapPackage("pois");
		morphia.mapPackage("tiposPoi");
		morphia.mapPackage("terminales");
		morphia.getMapper().getConverters().addConverter( new LocalDateConverter() );
		store = morphia.createDatastore(mongo, "BasePOIS");

	}
	
	@After
	public void terminar(){				// Elimina la base :)
		mongo.dropDatabase("BasePOIS");
	}

	@Test
	public void TestDireccionMongo(){
		Direccion dire=new Direccion();
		dire.setCalle("Baigorria");
		dire.setLatitud(100);
		dire.setLongitud(99);
		dire.setPiso(666);
		store.save(dire);
		Assert.assertEquals(dire.getPiso(),666);
	}
	
	@Test
	public void TestParadaColectivoMongo(){
		ParadaColectivo parada = new ParadaColectivo("PARADA 109");
		Direccion dire = new Direccion();
		dire.setCalle("Baigorria");
		dire.setBarrio("Devoto");
		dire.setNumero(666);
		dire.setLatitud(100);
		dire.setLongitud(99);
		parada.setDireccion(dire);
		store.save(parada);
		
		ParadaColectivo paradaBuscada = store.createQuery(ParadaColectivo.class)
				.filter("direccion.calle","Baigorria")
				.get();
		
		Assert.assertEquals(paradaBuscada.getNombre(),"PARADA 109");
		Assert.assertEquals(paradaBuscada.getDireccion().getCalle(),"Baigorria");
		Assert.assertEquals(paradaBuscada.getDireccion().getNumero(),666);
		Assert.assertEquals(paradaBuscada.getDireccion().getLatitud(),100,0);
		Assert.assertEquals(paradaBuscada.getDireccion().getBarrio(),"Devoto");
	}
	
	@Test
	public void TestBancoMongo(){
		Banco banco = new Banco();
			banco.setGerente("Batman");
			banco.setLatitud(1);
		Direccion dire = new Direccion();
			dire.setBarrio("Gotham");
			banco.setDireccion(dire);
			banco.setSucursal("Gotica");
			banco.setTags(Arrays.asList("Banco","Gotica","Batman","Plata"));		
		Servicio jubilacion = new Servicio("JUBILACION");
			jubilacion.setDescripcion("$$$$$$$$");
			jubilacion.setTags(Arrays.asList("Jubilacion","Servicio","QSY"));
		banco.setDireccion(dire);
		banco.agregarServicio(jubilacion);
		store.save(banco);
		
		Banco bancoLevantado = store.createQuery(Banco.class)
				.field("gerente")
				.equal("Batman")
				.get();
		
		Assert.assertEquals(bancoLevantado.getDireccion().getBarrio(),"Gotham");
		Assert.assertEquals(bancoLevantado.getTags().size(),4);
		Assert.assertEquals(bancoLevantado.getServicios().getServicios().size(),1);
	}
	
	@Test
	public void TestLocalMongo(){
		Local kiosco = new Local();
			kiosco.setNombre("kiosco");
		Rubro comercial = new Rubro("comercio");
			comercial.setRadioCercania(100);
		
		kiosco.setRubro(comercial);
		kiosco.setTags(Arrays.asList("Kiosco","Sube","chicles"));
		Direccion dire = new Direccion();
			dire.setCalle("Medrano");
		kiosco.setDireccion(dire);
		store.save(kiosco);
		
		Local kioscoBD = store.createQuery(Local.class)
				.field("nombre")
				.equal("kiosco")
				.get();
		
		Assert.assertEquals(kioscoBD.getRadioCercania(), 100,0);
		Assert.assertEquals(kioscoBD.getTags().size(), 3,0);
		Assert.assertEquals(kioscoBD.getRubro().getNombre(), "comercio");
	}
	
	@Test
	public void TestCGPMongo(){
		CGP cgp = new CGP();
			cgp.setNombre("cgp magico");
			cgp.setTags(Arrays.asList("Magia","Hechizos","Drogas"));
		Servicio magia = new Servicio();
			magia.setNombre("MAGIC");
			magia.setTags(Arrays.asList("Harry Potter","Desaparecer","Voldemort"));
		cgp.agregarServicio(magia);
		Comuna comuna = new Comuna();
			comuna.setNombre("Comuna 1");
		cgp.setComuna(comuna);
		store.save(cgp);
		
		CGP cgpBD = store.createQuery(CGP.class)
				.filter("tags", "Magia")
				.get();
		
		Assert.assertEquals(cgpBD.getNombre(), cgp.getNombre());
		Assert.assertTrue(cgpBD.tienePalabra("Harry Potter"));
		Assert.assertEquals(cgpBD.getServicios().getServicios().get(0).getTags().size(),3);		//el servicio tiene 3 tags
		Assert.assertEquals(cgpBD.getComuna().getNombre(), cgp.getComuna().getNombre());
	}
	
	@Test
	public void TestBusquedaMongo(){
		
		List<POI> resultados = new ArrayList();
		CGP cgp = new CGP();
			cgp.setNombre("cgp magico");
			resultados.add(cgp);
		Local kiosco = new Local();
			kiosco.setNombre("kiosco");
			resultados.add(kiosco);
			
		LocalDate fecha = LocalDate.now();
		
		Busqueda unaBusqueda=new Busqueda();
		unaBusqueda.setTiempoBusqueda(5);
		unaBusqueda.setFraseBuscada("parada");
		unaBusqueda.setResultados(resultados);
		unaBusqueda.setFecha(fecha);
		
		store.save(unaBusqueda);
		
		Busqueda busquedaDB = store.createQuery(Busqueda.class)
				.filter("fraseBuscada", "parada")
				.get();
		
		Assert.assertEquals(busquedaDB.getTiempoBusqueda(),5,0);	
		Assert.assertEquals(busquedaDB.getResultados().get(0).getNombre(), cgp.getNombre());
		Assert.assertEquals(busquedaDB.getResultados().get(1).getNombre(), kiosco.getNombre());
		Assert.assertEquals(busquedaDB.getFecha(), unaBusqueda.getFecha());
		
	}
	
	@Test
	public void TestBusquedaCache(){
		CGP cgp1 = new CGP();
		cgp1.setNombre("bla1");
		Direccion dire1 = new Direccion();
		dire1.setCalle("bla");
		cgp1.setDireccion(dire1);
		
		CGP cgp2 = new CGP();
		cgp2.setNombre("bla2");
		Direccion dire2 = new Direccion();
		dire2.setCalle("bla");
		cgp2.setDireccion(dire1);
		
		store.save(cgp1);
		store.save(cgp2);
		
		BufferBusquedas externos = new BufferBusquedas();
		List<POI> resultados = externos.buscar("bla", "");
		
		Assert.assertEquals(resultados.get(0).getNombre(), cgp1.getNombre());
		Assert.assertEquals(resultados.get(1).getNombre(), cgp2.getNombre());

	}
	
}
