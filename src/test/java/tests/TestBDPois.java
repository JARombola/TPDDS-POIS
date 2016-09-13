package tests;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import pois.Direccion;
import pois.Horario;
import tiposPoi.Banco;
import tiposPoi.ParadaColectivo;
import tiposPoi.Servicio;

public class TestBDPois extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private EntityManager em;
	
	@Before
	public void init(){
		em = PerThreadEntityManagers.getEntityManager();
	}
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
	@Test
	public void testPersistirDireccion(){		
		beginTransaction();
			Direccion dire= new Direccion();
			dire.setBarrio("Devoto");
			dire.setCalle("Beiro");
			dire.setDpto(20);
			dire.setLatitud(99);
			dire.setLongitud(2);
			persist(dire);
			commitTransaction();
		em.clear();
		
		Direccion direccionBuscada = (Direccion) em.createQuery("from Direccion where Barrio = :barrio")
				.setParameter("barrio", "Devoto").getSingleResult();
		assertEquals(direccionBuscada.getBarrio(),"Devoto");
		assertEquals(direccionBuscada.getCalle(),"Beiro");
		assertEquals(direccionBuscada.getLongitud(),2,0);
		assertEquals(direccionBuscada.getLatitud(),99,0);
		}
	
	@Test
	public void testPersistirParadaColectivo(){
		beginTransaction();
		ParadaColectivo parada1 = new ParadaColectivo();
			parada1.agregarTag("colectivo");
			parada1.agregarTag("lento");
			parada1.agregarTag("aburrido");
			parada1.setNombre("PARADA COLECTIVO");
		Direccion dire= new Direccion();
			dire.setLatitud(10);
			dire.setLongitud(20);
			dire.setBarrio("Palermo");
			parada1.setDireccion(dire);
		persist(parada1);
		commitTransaction();
		em.clear();		
		
		ParadaColectivo paradaBuscada = (ParadaColectivo) em.createQuery("from ParadaColectivo where Nombre = :nombre")
				.setParameter("nombre", "PARADA COLECTIVO")
				.getSingleResult();
		assertEquals(paradaBuscada.getTags().get(2),"aburrido");
		assertEquals(paradaBuscada.getTags().size(), 3);
		assertEquals(paradaBuscada.getDireccion().getLatitud(),10,0);
		assertEquals(paradaBuscada.getDireccion().getBarrio(),"Palermo");
		}		
	
	@Test
	public void testPersistenciaBanco(){
		beginTransaction();
			Banco banco = new Banco();
			banco.setNombre("Patagonia");
			Servicio jubilacion = new Servicio("JUBILACION");
				jubilacion.setDescripcion("$$$$$$");
				jubilacion.agregarTag("Plata");
			Horario horaJubilacion=new Horario();
				horaJubilacion.setDia(1);
			LocalTime horaInicio=new LocalTime(10,00);
			LocalTime horaCierre=new LocalTime(22,00);
				horaJubilacion.setInicio(horaInicio);
				horaJubilacion.setFin(horaCierre);
			jubilacion.agregarHorario(horaJubilacion);
			banco.agregarServicio(jubilacion);
			banco.setGerente("Julian");
			banco.setLatitud(222);
			
		persist(banco);
		commitTransaction();
		em.clear();		
		
		Banco bancoBuscado = (Banco) em.createQuery("from Banco where Gerente = :gerente")
				.setParameter("gerente", "Julian")
				.getSingleResult();
		assertEquals(bancoBuscado.getGerente(),"Julian");
		assertEquals(bancoBuscado.getDireccion().getLatitud(),222,0);	// => Persistio la Direccion => Persistio Coordenadas :)
		assertEquals(bancoBuscado.getServicios().getServicios().size(),1,0);	// Persistió los servicios
		assertTrue(bancoBuscado.estaDisponible(1,new LocalTime(11,00),"JUBILACION"));	//Persistió tambien sus horarios :)
		
		
	}
	
	/*@Test
	public void testBusqueda(){
		EntityManager em = 
				PerThreadEntityManagers.
				getEntityManager();
		
		beginTransaction();
		
		ParadaColectivo parada1, parada2, parada3;
		Mapa mapa;
		BufferBusquedas buffer;
		BuscadorBancoExterno buscadorBanco;
		BuscadorCGPExterno buscadorCgp;
		OrigenDatos origenBanco,origenCGP,origen3;
		Terminal terminal;
		List<Busqueda> historial;
		Busqueda busquedaBuscada;
		
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
		mapa = new Mapa();
			mapa.setPOI(parada1);
			mapa.setPOI(parada2);
			mapa.setPOI(parada3);

			
		buffer=new BufferBusquedas();
			buffer.agregarExterno(buscadorBanco);
			buffer.agregarExterno(buscadorCgp);
		terminal = new Terminal();
			terminal.setBuffer(buffer);
			terminal.setMapa(mapa);
			
			terminal.iniciarBusqueda("114","");
			historial = terminal.getHistorialBusquedas();
			
			persist(historial.get(0));
			commitTransaction();
		em.clear();
		
		List<Busqueda> busquedaBuscada = em.find(Busqueda.class, 1);
		
		assertEquals(busquedaBuscada.getFraseBuscada(),"114");
		assertEquals(busquedaBuscada.getCantidadResultados()),3);
		
	}*/
	
	
	

}
