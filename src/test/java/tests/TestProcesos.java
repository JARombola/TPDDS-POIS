package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import configuracionTerminales.Administrador;
import procesos.ProcesoActualizacionLocalesComerciales;
import terminales.ControlTerminales;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.Local;

public class TestProcesos {
	private Administrador admin;
	private Local poiLocal1, libreria;
	private Mapa mapa;
	private Terminal terminal;
	private ControlTerminales controlTerminales;
	private ProcesoActualizacionLocalesComerciales proceso;
	
	@Before
	public void initialize() {
		controlTerminales = new ControlTerminales();

		terminal = new Terminal();
		
		mapa = Mapa.getInstance();
		terminal.setMapa(mapa);
		terminal.setAdministrador(admin);
		proceso=new ProcesoActualizacionLocalesComerciales("test.txt");
		controlTerminales.agregarTerminal(terminal);
		
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
						
		mapa.setPOI(poiLocal1);
		mapa.setPOI(libreria);
	}
	
	
	@Test
	public void testProcesoActualizacionLocalesComerciales(){
		
		Assert.assertEquals(poiLocal1.getTags().size(),3);
		Assert.assertEquals(libreria.getTags().size(),5);
		proceso.run();
		Assert.assertEquals(poiLocal1.getTags().size(),4);
		Assert.assertEquals(libreria.getTags().size(),10);
		Assert.assertEquals(poiLocal1.getTags().get(0),"a");		//cambiaron!! :)
		Assert.assertEquals(libreria.getTags().get(0), "1");
		Assert.assertEquals(libreria.getTags().get(1), "2");
	}	
	
}
