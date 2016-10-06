package tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import procesos.ManejoDeResultadosProcesos;
import procesos.ProcesoActualizacionLocalesComerciales;
import procesos.ProcesoBajaPOIs;
import procesos.ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra;
import terminales.ControlTerminales;
import terminales.Mapa;
import terminales.Terminal;
import tiposPoi.Local;

public class TestProcesos {
	private Administrador admin;
	private Local poi;
	private Mapa mapa;
	private ProcesoActualizacionLocalesComerciales proceso, localesMock;
	private ProcesoBajaPOIs bajaMock;
	private ControlProcesos controlProcesos;
	private ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra procesoNegro1, procesoNegro2;
	private Terminal terminal;
	private ControlTerminales controlTerminales;
	private ManejoDeResultadosProcesos manejoMock;
	
	@Before
	public void initialize() {
		
		manejoMock=Mockito.mock(ManejoDeResultadosProcesos.class);
		
		controlProcesos = new ControlProcesos();
		controlProcesos.setManejoResultados(manejoMock);
		controlProcesos=new ControlProcesos();
	
		controlTerminales = new ControlTerminales();
		
		terminal = new Terminal();
		
		procesoNegro1 = new ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra();
		procesoNegro2 = new ProcesoNegroParaTestearLosTiemposPorqueNoQuedaOtra();
		
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
		
		admin=new Administrador("asdasd@gmail.com");

		mapa = Mapa.getInstance();
		terminal.setMapa(mapa);
		terminal.setAdministrador(admin);
		proceso=new ProcesoActualizacionLocalesComerciales("test.txt");
		controlTerminales.agregarTerminal(terminal);
		
		poi = new Local();
		poi.agregarTag("a");
		poi.agregarTag("b");
		poi.agregarTag("c");
		poi.setNombre("kosiuko");
		mapa.setPOI(poi);
	}
	

	//@Test
	public void testEjecucionDeProcesos(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date) + ": hora actual");
		procesoNegro1.setFecha(date);
		procesoNegro1.setReintentos(0);
		controlProcesos.agregarProceso(procesoNegro1);
		procesoNegro2.setFecha(date);
		procesoNegro2.setReintentos(1);
		controlProcesos.agregarProceso(procesoNegro2);
		
		try {
		TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		} 
//		TODO: Bueno ni hablar de que este "test" no es automatizable. 
//		Había una forma más fácil de hacerlo (que usando time como usaron ustedes) y hubiese
//		sido más fácil de testear. Pero bueno... Habría que investigar cómo se puede testear esto, tiene que haber una manera.
//		Pero se las dejo pasar porque hay muchas cosas para hacer. Simplemente borren todo esto por favor - Aldana.
		System.out.println("Entre cada una de estas horas tiene que haber 2 segundos de diferencia. Si no, es porque rompieron algo, y lo arreglan c:");
	}
	
	@Test
	public void testProcesoActualizacionLocalesComerciales(){
		proceso.run();
		Assert.assertEquals(poi.getTags().size(),4);
	}	
	
}
