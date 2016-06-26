package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import principal.POIS.TiposPOI.Local;
import principal.Terminales.Mapa;
import procesos.ProcesoActualizacionLocalesComerciales;
import configuracionTerminales.Administrador;

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
		
		Administrador admin = new Administrador();
		
		
			
		
		proceso=new ProcesoActualizacionLocalesComerciales("C:\\Users\\JULIETA\\test.txt", admin);
		proceso.setMapa(mapa);

		
	}
	@Test
	public void testProcesoActualizacionLocalesComerciales(){
		proceso.run();
		Assert.assertEquals(poi.getTags().size(),4);
	}
	
	
}
