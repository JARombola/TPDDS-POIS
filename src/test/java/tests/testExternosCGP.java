package tests;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import externos.BufferBusquedas;
import externos.CentroDTO;
import externos.RangosServiciosDTO;
import externos.ServiciosDTO;
import principal.Horario;
import tipos.Servicio;

public class testExternosCGP {
	CentroDTO cgp;
	RangosServiciosDTO rangoServicio;
	RangosServiciosDTO rangoServicio2;
	RangosServiciosDTO rangoServicio3;
	ServiciosDTO servicioDTOTramite;
	ServiciosDTO servicioDTOCobro;
	BufferBusquedas buffer;
	
	@Before
	public void initialize() {
		cgp = new CentroDTO();
			cgp.setDomicilio("Av. 9 de Julio 4322");
		rangoServicio = new RangosServiciosDTO();
			rangoServicio.setDia(1);
			rangoServicio.setHoraInicio(8);
			rangoServicio.setMinutoInicio(30);
			rangoServicio.setHoraFin(19);
			rangoServicio.setMinutoFin(15);
		rangoServicio2 = new RangosServiciosDTO();
			rangoServicio2.setDia(2);
			rangoServicio2.setHoraInicio(7);
			rangoServicio2.setMinutoInicio(00);
			rangoServicio2.setHoraFin(21);
			rangoServicio2.setMinutoFin(15);
		rangoServicio3 = new RangosServiciosDTO();
			rangoServicio3.setDia(3);
			rangoServicio3.setHoraInicio(7);
			rangoServicio3.setMinutoInicio(00);
			rangoServicio3.setHoraFin(21);
			rangoServicio3.setMinutoFin(15);
		servicioDTOTramite = new ServiciosDTO();
			servicioDTOTramite.agregarRango(rangoServicio);
			servicioDTOTramite.agregarRango(rangoServicio2);
			servicioDTOTramite.setNombre("Tramite Jubilacion");
		servicioDTOCobro = new ServiciosDTO();
			servicioDTOCobro.agregarRango(rangoServicio3);
			servicioDTOCobro.setNombre("Cobro Jubilacion");
		buffer = new BufferBusquedas();
	}
	
	@Test
	public void testDomicilioCalle() {
		Assert.assertEquals(cgp.getCalle(),"Av. 9 de Julio");
	}
	
	@Test
	public void testDomicilioNumero() {
		Assert.assertEquals(cgp.getNumero(),4322);
	}
	
	@Test
	public void testAdaptarAHorarioLocalTime(){
		Horario horario = buffer.adaptarAHorarioLocalTime(rangoServicio);
		Assert.assertEquals(horario.getInicio(),new LocalTime(8,30));
		Assert.assertEquals(horario.getFin(),new LocalTime(19,15));
	}
	
	@Test
	public void testAdaptarSerivicio(){
		Servicio servicioPOI = buffer.adaptarSerivicio(servicioDTOTramite);
		Assert.assertEquals(servicioPOI.getNombre(),"Tramite Jubilacion");
		Assert.assertEquals(servicioPOI.getHorarios().getHorariosAtencion().get(0).getInicio(),new LocalTime(8,30));
		Assert.assertEquals(servicioPOI.getHorarios().getHorariosAtencion().get(0).getDia(),1);
		Assert.assertEquals(servicioPOI.getHorarios().getHorariosAtencion().get(1).getFin(),new LocalTime(21,15));
	}
	
	//Faltaria un test final para de adaptarCGP, para ver si funca adaptando TODA la
	// lista de servicios de un CGP. Que es alto bardo. Suerte.
	

}