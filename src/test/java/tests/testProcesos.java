package tests;

import org.junit.Assert;
import org.junit.Test;

import org.junit.Before;

import principal.POIS.TiposPOI.Local;
import principal.Terminales.Mapa;
import procesos.ProcesoActualizacionLocalesComerciales;

public class testProcesos {
	Local poi;
	Mapa mapa;
	ProcesoActualizacionLocalesComerciales proceso;
	
	@Before
	public void initialize() {
		poi = new Local();
		poi.agregarTag("a");
		poi.agregarTag("b");
		poi.agregarTag("c");
		poi.setNombre("kosiuko");
			
		mapa = new Mapa();
		mapa.setPOI(poi);
		mapa.setPOI(poi);
			
		proceso=new ProcesoActualizacionLocalesComerciales("C:\\Users\\JULIETA\\test.txt");
		proceso.setMapa(mapa);

		
	}
	@Test
	public void testProcesoActualizacionLocalesComerciales(){
		Assert.assertEquals(poi.getTags().size(),3);
		proceso.run();
		Assert.assertEquals(poi.getTags().size(),4);
	}
	
	
}
