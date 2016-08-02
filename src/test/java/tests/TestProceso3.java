package tests;


import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import configuracionTerminales.Administrador;
import configuracionTerminales.FuncionesExtra;
import principal.POIS.Comuna;
import principal.POIS.Coordenadas;
import principal.Terminales.ControlTerminales;
import principal.Terminales.Terminal;
import procesos.ControlProcesos;
import procesos.ProcesoAgregarAccionesParaUsuarios;

public class TestProceso3 {
	private ControlTerminales controlMaestro;
	private Terminal terminal1, terminal2, terminal3;
	private Comuna comuna;
	private Coordenadas coordenada1,coordenada2,coordenada3;
	private ControlProcesos controlProcesos;
	private Administrador admin;
	private FuncionesExtra opciones;

	@Before
	public void intialize() {
		controlProcesos = new ControlProcesos();
		admin = new Administrador();
	
	


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

		controlMaestro = new ControlTerminales();
		opciones = new FuncionesExtra(100);
		terminal1 = new Terminal();
		terminal2 = new Terminal();
		terminal3 = new Terminal();
		terminal1.setCoordenadas(coordenada1);
		terminal2.setCoordenadas(coordenada1);
		terminal3.setCoordenadas(coordenada1);
		
		terminal1.setExtra(opciones);
		terminal2.setExtra(opciones);
		terminal3.setExtra(opciones);
		
		controlMaestro.agregarTerminal(terminal1);
		controlMaestro.agregarTerminal(terminal2);
		controlMaestro.agregarTerminal(terminal3);
		
		
		
	}

	@Test
	public void testActivarMailEnUnaTerminal() {
		Assert.assertEquals(terminal1.getExtra().getOpciones().get("MAIL"), false);
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlMaestro, terminal1,"MAIL", admin);
		proceso.run();
		Assert.assertEquals(terminal1.getExtra().getOpciones().get("MAIL"), true);
	}
	@Test
	public void testActivarBusquedasEnUnaComuna() {
		Assert.assertEquals(terminal1.getExtra().getOpciones().get("HISTORIAL"), false);
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlMaestro, comuna, "HISTORIAL", admin);
		proceso.run();
		Assert.assertEquals(terminal1.getExtra().getOpciones().get("HISTORIAL"), true);			//las 2 terminales estan en esa comuna, y se les activa el Historial
		Assert.assertEquals(terminal2.getExtra().getOpciones().get("HISTORIAL"), true);
	}
	@Test
	public void testActivarMailEnTodasLasTerminales() {
		Assert.assertEquals(terminal1.getExtra().getOpciones().get("MAIL"), false);
		Assert.assertEquals(terminal2.getExtra().getOpciones().get("MAIL"), false);
		ProcesoAgregarAccionesParaUsuarios proceso = new ProcesoAgregarAccionesParaUsuarios(controlMaestro, "MAIL", admin);
		proceso.run();
		Assert.assertEquals(terminal1.getExtra().getOpciones().get("MAIL"), true);			//las 2 terminales estan en esa comuna, y se les activa el Historial
		Assert.assertEquals(terminal2.getExtra().getOpciones().get("MAIL"), true);
	}
	

}
