package tests;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import externos.BufferBusquedas;
import externos.CentroDTO;
import externos.RangosServiciosDTO;
import externos.ServiciosDTO;
import principal.Horario;
import tipos.CGP;
import tipos.Servicio;

public class testExternosCGP {
	RangosServiciosDTO rangoServicio, rangoServicio2, rangoServicio3;
	ServiciosDTO servicioDTOTramite, servicioDTOCobro;
	CentroDTO cgp,cgpMock;
	BufferBusquedas buffer;
	List<ServiciosDTO> servicios;
	
	@Before
	public void initialize() {
		cgpMock=Mockito.mock(CentroDTO.class);
		cgpMock.setDomicilio("Av. 9 de Julio 4322");
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
		servicios=new ArrayList<ServiciosDTO>();
		servicios.add(servicioDTOCobro);
		servicios.add(servicioDTOTramite);
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
	
	@Test
	public void testAdaptarCgp(){
		cgp.setServicios(servicios);
		CGP poicgp= buffer.adaptarCGP(cgp);
		Assert.assertEquals(poicgp.getNombre(),("Av. 9 de Julio 4322"));
		Assert.assertEquals(poicgp.getServicios().getServicios().size(),cgp.getServicios().size());
	}
	
	//Faltaria un test final para de adaptarCGP, para ver si funca adaptando TODA la
	// lista de servicios de un CGP. Que es alto bardo. Suerte.
	

}