package tests;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import pois.POI;
import procesos.ActualizacionLocales;
import terminales.RepositorioTerminales;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.Local;

public class TestProcesos extends AbstractPersistenceTest implements WithGlobalEntityManager{
	private Local poiLocal1, libreria;
	private Mapa mapa;
	private Terminal terminal;
	private RepositorioTerminales RepositorioTerminales;
	private ActualizacionLocales proceso;
	
	@SuppressWarnings("unchecked")
	@After
	public void eliminarPois(){
		List<POI> p = createQuery("from POI").getResultList();
		p.stream().forEach(e->mapa.eliminarPOI(e.getId()));
	}
	
	@Before
	public void initialize() {
		RepositorioTerminales = new RepositorioTerminales();

		terminal = new Terminal();
		
		mapa = Mapa.getInstancia();
		terminal.setMapa(mapa);

		RepositorioTerminales.actualizar(terminal);
		
		poiLocal1 = new Local();
			poiLocal1.agregarTag("ASD");
			poiLocal1.agregarTag("BBB");
			poiLocal1.agregarTag("CCC");
			poiLocal1.setNombre("kosiuko");
		
		libreria = new Local();
			libreria.setNombre("LIBRERIA");
			libreria.agregarTag("Libreria");
			libreria.agregarTag("Libros");
			libreria.agregarTag("Lapices");
			libreria.agregarTag("Colegio");
			libreria.agregarTag("Cuaderno");
						
		mapa.agregarOmodificar(poiLocal1);
		mapa.agregarOmodificar(libreria);
	}
	
	
	@Test
	public void TestProcesoActualizacionLocalesComerciales(){
		Assert.assertEquals(poiLocal1.getTags().size(),3);
		Assert.assertEquals(libreria.getTags().size(),5);
		proceso=new ActualizacionLocales("test.txt");
		proceso.run();
		poiLocal1 = (Local) Mapa.getInstancia().getPOI(poiLocal1.getId());
		libreria = (Local) Mapa.getInstancia().getPOI(libreria.getId());
		Assert.assertEquals(poiLocal1.getTags().size(),4);
		Assert.assertEquals(libreria.getTags().size(),10);
		Assert.assertEquals(poiLocal1.getTags().get(0),"a");		//cambiaron!! :)
		Assert.assertEquals(libreria.getTags().get(0), "1");
		Assert.assertEquals(libreria.getTags().get(1), "2");
	}	
	
}
