package tests;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import pois.Coordenadas;
import pois.Direccion;
import tiposPoi.ParadaColectivo;

public class TestBDPois extends AbstractPersistenceTest implements WithGlobalEntityManager {

	
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
		EntityManager em = 
				PerThreadEntityManagers.
				getEntityManager();
		
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
		
		Direccion direccionBuscada = em.find(Direccion.class, 1);
		assertEquals(direccionBuscada.getBarrio(),"Devoto");
		assertEquals(direccionBuscada.getCalle(),"Beiro");
		assertEquals(direccionBuscada.getLongitud(),2,0);
		assertEquals(direccionBuscada.getLatitud(),99,0);
		}
	
	@Test
	public void testPersistirParadaColectivo(){
		EntityManager em = PerThreadEntityManagers.getEntityManager();
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
		
		ParadaColectivo paradaBuscada = em.find(ParadaColectivo.class, 1);
		
		assertEquals(paradaBuscada.getNombre(),"PARADA COLECTIVO");
		assertEquals(paradaBuscada.getTags().get(2),"aburrido");
		assertEquals(paradaBuscada.getTags().size(), 3);
		assertEquals(paradaBuscada.getDireccion().getLatitud(),10,0);
		assertEquals(paradaBuscada.getDireccion().getBarrio(),"Palermo");
		
		}		

}
