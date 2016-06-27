package tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import principal.POIS.TiposPOI.Local;
import principal.Terminales.Mapa;
import procesos.ControlProcesos;
import procesos.ProcesoActualizacionLocalesComerciales;
import procesos.ProcesoAgregarAccionesParaUsuarios;
import procesos.ProcesoBajaPOIs;
import configuracionTerminales.Administrador;
import externos.OrigenDatos;

public class testProcesos {
	Local poi;
	Mapa mapa;
	ProcesoActualizacionLocalesComerciales proceso;
	ProcesoActualizacionLocalesComerciales localesMock;
	ProcesoBajaPOIs bajaMock;
	ControlProcesos controlProcesos;
	Timer timer;
	
	@Before
	public void initialize() {

		timer = new Timer();
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
		controlProcesos.setTimer(timer);
		

		proceso=new ProcesoActualizacionLocalesComerciales("test.txt");
		proceso.setMapa(mapa);
		
		poi = new Local();
		poi.agregarTag("a");
		poi.agregarTag("b");
		poi.agregarTag("c");
		poi.setNombre("kosiuko");
		mapa.setPOI(poi);

	}
	
	/*@Test
	public void testEjecucionDeProcesos(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		controlProcesos.agregarProceso(localesMock,date);
		controlProcesos.agregarProceso(bajaMock, date);
		
		try {
		TimeUnit.SECONDS.sleep(7);
		} catch (InterruptedException e) {
		} 
		
	}*/
	
	@Test
	public void testProcesoActualizacionLocalesComerciales(){
		proceso.run();
		Assert.assertEquals(poi.getTags().size(),4);
	}
	
	
}
