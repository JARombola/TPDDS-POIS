package tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Query;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import pois.POI;
import terminales.Mapa;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;

public class TestABMDesdeCodigo extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	private Mapa mapa;
	private CGP cgp;
	private Banco banco;
	private ParadaColectivo parada;

	@After
	public void eliminarPois(){
		List<POI> p = createQuery("from POI").getResultList();
		p.stream().forEach(e->remove(e));
	}
	
	public void guardarPois(){
		mapa = new Mapa();
		cgp = new CGP();
			cgp.setNombre("CGP");
			cgp.agregarTag("TAG1");
			cgp.agregarTag("TAG2");
				
		banco = new Banco();
				banco.setGerente("Julian");
				banco.setNombre("Banquito");
				
		parada = new ParadaColectivo();
				parada.setNombre("Parada Colectivo");
				parada.setLatitud(666);
			mapa.agregarOmodificar(parada);
			mapa.agregarOmodificar(banco);
			mapa.agregarOmodificar(cgp);
		commitTransaction();
	}
		
	@Test
	public void testGuardarPoi(){				//Guarda poi en el mapa, y tiene que persistirlo
		Local local=new Local();
				local.setNombre("LocalComercial");
				local.agregarTag("Kiosco");
				local.agregarTag("Local");
		mapa = new Mapa();
		mapa.agregarOmodificar(local);
		Local buscado=(Local) entityManager()
								.createQuery("from POI where Nombre = :nombre")
								.setParameter("nombre", "LocalComercial")
								.getSingleResult();
		
		assertEquals(buscado.getTags().size(), 2);
		buscado.agregarTag("TagNuevo3");
		mapa.agregarOmodificar(buscado);
		
		@SuppressWarnings("unchecked")
		List<Local> localModificado = (List<Local>) entityManager().createQuery("from POI where Nombre = :nombre")
							.setParameter("nombre", "LocalComercial")
							.getResultList();
		assertEquals(localModificado.size(), 1);			//Para verificar que lo haya modificado (en vez de crear y persistir uno nuevo)
		assertEquals(buscado.getTags().size(), 3);			//le agregamos un tag
		mapa.eliminarPOI(buscado.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEliminarPoi(){
		guardarPois();
		CGP cgp2 = new CGP();
			cgp2.setNombre("CGP2");
			cgp2.agregarTag("cgpTag1");
			cgp2.agregarTag("cgpTag2");
		
		Query buscarTodos = entityManager().createQuery("from POI");
		List<POI> poisGuardados =buscarTodos.getResultList();
			
		assertEquals(poisGuardados.size(), 3);		
		
		mapa.agregarOmodificar(cgp2);					//se guardó un poi más => 4
		poisGuardados = buscarTodos.getResultList();
		assertEquals(poisGuardados.size(), 4);		
		
		mapa.eliminarPOI(cgp2.getId());	
		poisGuardados = buscarTodos.getResultList();
		assertEquals(poisGuardados.size(), 3);			//se eliminó el ultimo => 3
	}
}
