package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import terminales.Mapa;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;

public class TestABMDesdeCodigo extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	private Mapa mapa;

	public void guardarPois(){
		beginTransaction();
			CGP cgp = new CGP();
				cgp.setNombre("CGP");
				cgp.agregarTag("TAG1");
				cgp.agregarTag("TAG2");
				
			Banco banco = new Banco();
				banco.setGerente("Julian");
				banco.setNombre("Banquito");
				
			ParadaColectivo parada = new ParadaColectivo();
				parada.setNombre("Parada Colectivo");
				parada.setLatitud(666);
			persist(parada);
			persist(banco);
			persist(cgp);
		commitTransaction();
		mapa=Mapa.getInstancia();
		
	}
	
//	@After @Before
	public void cleanEntity(){
		if(entityManager().getTransaction().isActive()) rollbackTransaction();
	}

		
	@Test
	public void testGuardarPoi(){				//Guarda poi en el mapa, y tiene que persistirlo
		Local local=new Local();
				local.setNombre("LocalComercial");
				local.agregarTag("Kiosco");
				local.agregarTag("Local");
		mapa=new Mapa();
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
	}
	
	@Test
	public void testEliminarPoi(){
		guardarPois();
		CGP cgp2 = new CGP();
			cgp2.setNombre("CGP2");
			cgp2.agregarTag("cgpTag1");
			cgp2.agregarTag("cgpTag2");
		
		assertEquals(mapa.getListaPOIS().size(), 3);		//se guardó un poi más
		mapa.agregarOmodificar(cgp2);
		assertEquals(mapa.getListaPOIS().size(), 4);		
		mapa.eliminarPOI(cgp2.getId());						
		assertEquals(mapa.getListaPOIS().size(), 3);		//se eliminó el ultimo
	}
	
}
