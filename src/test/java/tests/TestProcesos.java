package tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import configuracionTerminales.Administrador;
import procesos.ControlProcesos;
import procesos.ProcesoActualizacionLocalesComerciales;
import procesos.ProcesoBajaPOIs;
import procesos.ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra;
import terminales.Mapa;
import tiposPoi.Local;

public class TestProcesos {
	Local poi;
	Mapa mapa;
	ProcesoActualizacionLocalesComerciales proceso;
	ProcesoActualizacionLocalesComerciales localesMock;
	ProcesoBajaPOIs bajaMock;
	ControlProcesos controlProcesos;
	ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra procesoNegro1;
	ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra procesoNegro2;
	private Administrador admin;
	
	
	@Before
	public void initialize() {
		admin = new Administrador();
		procesoNegro1 = new ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra(admin);
		procesoNegro2 = new ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra(admin);
		
		localesMock=Mockito.mock(ProcesoActualizacionLocalesComerciales.class);
		bajaMock=Mockito.mock(ProcesoBajaPOIs.class);

		Mockito.doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		    	try {
		    		TimeUnit.SECONDS.sleep(2);
					DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date2 = new Date();
					System.out.println(dateFormat2.format(date2));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		       return null;
		    }
		}).when(localesMock).run();
		
		Mockito.doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		    	try {
		    		TimeUnit.SECONDS.sleep(4);
					DateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date3 = new Date();
					System.out.println(dateFormat3.format(date3));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		       return null;
		    }
		}).when(bajaMock).run();
		

		mapa = new Mapa();
		controlProcesos = new ControlProcesos();
		controlProcesos.setMapa(mapa);
		Administrador admin = new Administrador();

		proceso=new ProcesoActualizacionLocalesComerciales("test.txt", admin);
		proceso.setMapa(mapa);
		
		poi = new Local();
		poi.agregarTag("a");
		poi.agregarTag("b");
		poi.agregarTag("c");
		poi.setNombre("kosiuko");
		mapa.setPOI(poi);

	}
	
	@Test
	public void testEjecucionDeProcesos(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date) + ": hora actual");

		controlProcesos.agregarProceso(procesoNegro1,date,0);
		controlProcesos.agregarProceso(procesoNegro2, date,1);
		
		try {
		TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		} 
		
		System.out.println("Entre cada una de estas horas tiene que haber 2 segundos de diferencia. Si no, es porque rompieron algo, y lo arreglan c:");
	}
	
	@Test
	public void testProcesoActualizacionLocalesComerciales(){
		proceso.run();
		Assert.assertEquals(poi.getTags().size(),4);
	}

	
}
