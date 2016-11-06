package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import configuracionTerminales.Administrador;
import configuracionTerminales.EnviadorMails;
import configuracionTerminales.FuncionesExtra;
import pois.Comuna;
import pois.Coordenadas;
import procesos.AgregarAcciones;
import terminales.RepositorioTerminales;
import terminales.Terminal;


public class TestProceso3 {
	private Administrador admin;
	private RepositorioTerminales controlMaestro;
	private Terminal terminal1, terminal2, terminal3, terminal4;
	private Comuna comuna;
	private Coordenadas coordenada1, coordenada2, coordenada3, coordenada4;
	private FuncionesExtra opciones;
	private EnviadorMails mailMock;

	@Before
	public void intialize() {
		mailMock = Mockito.mock(EnviadorMails.class);
		EnviadorMails.setInstancia(mailMock);
		
		admin=new Administrador("mail");
			admin.setNombre("pepe");
		
		comuna = new Comuna();
		coordenada1 = new Coordenadas();
		coordenada1.setLatitud(47);
		coordenada1.setLongitud(-120);
		comuna.addPunto(coordenada1);
		
		coordenada2 = new Coordenadas();
		coordenada2.setLatitud(45);
		coordenada2.setLongitud(-130);
		comuna.addPunto(coordenada2);
		
		coordenada3 = new Coordenadas();
		coordenada3.setLatitud(50);
		coordenada3.setLongitud(-130);
		comuna.addPunto(coordenada3);
		
		coordenada4 = new Coordenadas();
		coordenada4.setLatitud(3);
		coordenada4.setLongitud(8);
		
		controlMaestro = RepositorioTerminales.getInstancia();
		
		opciones = new FuncionesExtra(100);
		
		terminal1 = new Terminal();
		terminal2 = new Terminal();
		terminal3 = new Terminal();
		terminal4 = new Terminal();
		
		terminal1.setCoordenadas(coordenada1);
		terminal2.setCoordenadas(coordenada1);
		terminal3.setCoordenadas(coordenada1);
		terminal4.setCoordenadas(coordenada4);
		
		terminal1.setExtra(opciones);
		terminal2.setExtra(opciones);
		terminal3.setExtra(opciones);
		terminal4.setExtra(opciones);
		
		
		controlMaestro.actualizar(terminal1);
		controlMaestro.actualizar(terminal2);
		controlMaestro.actualizar(terminal3);
		controlMaestro.actualizar(terminal4);			
	}
	
	@After
	public void limpiarCentralTerminales(){
		controlMaestro.eliminarTerminales();
	}
	

	
	@Test
	public void testActivarMailEnUnaTerminal() {
		Assert.assertEquals(terminal1.estaActivado("MAIL"), false);
		AgregarAcciones proceso = new AgregarAcciones( "MAIL");
		proceso.agregarAccionTerminal(terminal1);
		proceso.run();
		Assert.assertEquals(terminal1.estaActivado("MAIL"), true);
		Assert.assertEquals(proceso.getCantidadAfectados(),1);						//Se modifico UNA terminal
	}
	
	@Test
	public void testActivarBusquedasEnUnaComuna() {
		Assert.assertEquals(terminal1.estaActivado("HISTORIAL"), false);
		Assert.assertEquals(terminal4.estaActivado("HISTORIAL"), false);
		AgregarAcciones proceso = new AgregarAcciones("HISTORIAL");
		proceso.AgregarAccionComuna(comuna);
		proceso.run();
		Assert.assertEquals(terminal1.estaActivado("HISTORIAL"), true);			//las 2 terminales estan en esa comuna, y se les activa el Historial
		Assert.assertEquals(terminal2.estaActivado("HISTORIAL"), true);
		Assert.assertEquals(terminal3.estaActivado("HISTORIAL"), true);
		Assert.assertEquals(terminal4.estaActivado("HISTORIAL"), true);
		Assert.assertEquals(proceso.getCantidadAfectados(),4);					//Se modificaron las 3 terminales
	}
	
	@Test
	public void testActivarMailEnTodasLasTerminales() {
		Assert.assertEquals(terminal1.estaActivado("MAIL"), false);
		Assert.assertEquals(terminal2.estaActivado("MAIL"), false);
		AgregarAcciones proceso = new AgregarAcciones("MAIL");
		proceso.agregarAccionTodasTerminales();
		proceso.run();
		Assert.assertEquals(terminal1.estaActivado("MAIL"), true);			//las 2 terminales estan en esa comuna, y se les activa el Historial
		Assert.assertEquals(terminal2.estaActivado("MAIL"), true);
		Assert.assertEquals(proceso.getCantidadAfectados(),4);					//Se modificaron todas las terminales (4)
	}
	
	@Test
	public void testFallaProceso() {
		AgregarAcciones proceso = new AgregarAcciones("ASD");	
		proceso.agregarAccionTerminal(terminal1);
		proceso.run();						//Falla porque la opcion "ASD" es incorrecta => llama al metodo del mock para el manejo del error.
		Mockito.verify(mailMock,Mockito.times(0)).mailFallaProceso(Mockito.any());	//mail desactivado => no llama al mock
		proceso.setOpcionMail(true);
		proceso.run();
		Mockito.verify(mailMock,Mockito.times(1)).mailFallaProceso(Mockito.any());
		
	}

}
