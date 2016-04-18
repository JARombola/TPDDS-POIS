package principal;

import static org.junit.Assert.*;

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Servicio;

public class testHorarios {
	Banco banco;
	ParadaColectivo parada;
	Local carrousel;
	CGP unCGP;
	LocalTime horaInicio,horaCierre,horaX;
	
	@Before
	public void initialize() {
		banco = new Banco();
		Servicio rentas=new Servicio("Rentas");
		horaInicio=new LocalTime(10,00);
		horaCierre=new LocalTime(22,00);
		rentas.getHorarios().horarioNuevo(1, horaInicio, horaCierre);
		Servicio jubilacion=new Servicio("jubilacion");
		horaInicio=new LocalTime(5,00);
		horaCierre=new LocalTime(9,00);
		jubilacion.getHorarios().horarioNuevo(2, horaInicio, horaCierre);

		banco.agregarServicio(rentas);
		banco.agregarServicio(jubilacion);
		
		unCGP = new CGP();
		unCGP.agregarServicio(rentas);
		unCGP.agregarServicio(jubilacion);
		
		parada = new ParadaColectivo();
		carrousel = new Local();
		for (int dia = 2; dia <= 7; dia++) {		//Horarios Carrousel 
			horaInicio=new LocalTime(10,00);
			horaCierre=new LocalTime(13,00);
			carrousel.getHorarios().horarioNuevo(dia, horaInicio, horaCierre);
			horaInicio=new LocalTime(17,00);
			horaCierre=new LocalTime(20,30);
			carrousel.getHorarios().horarioNuevo(dia, horaInicio, horaCierre);
		}

	}

	@Test
	public void testHorariosBanco() {
		horaX=new LocalTime(12,00);
		boolean domingo = banco.estaDisponible(7, horaX); // Banco un domingo? Ja
		assertEquals(false, domingo);
		horaX=new LocalTime(14,00);
		boolean lunes = banco.estaDisponible(2, horaX);
		assertEquals(true, lunes);
		horaX= new LocalTime (16,00);
		boolean viernesTarde = banco.estaDisponible(5, horaX);	//demasiado temprano...
		assertEquals(false, viernesTarde);
	}
	@Test
	public void testServiciosBanco(){
		horaX=new LocalTime(12,00);
		boolean abierto=banco.estaDisponible(1, horaX, "Rentas");
		assertEquals(true, abierto);
		horaX=new LocalTime(9,00);
		boolean abiertoRentas=banco.estaDisponible(1,horaX,"rentas");
		assertEquals(false, abiertoRentas);
		horaX=new LocalTime(8,00);
		boolean abiertoJubilacion=banco.estaDisponible(2, horaX, "jUBiLaciOn");
		assertEquals(true,abiertoJubilacion);
	}
	@Test
	public void testHorarioCGP(){
		horaX=new LocalTime(12,00);
		boolean abierto=unCGP.estaDisponible(1, horaX, "Rentas");		
		assertEquals(true,abierto);
		horaX = new LocalTime(6,00);
		abierto=unCGP.estaDisponible(4, horaX);		
		assertEquals(false,abierto);
		horaX=new LocalTime(6,00);
		abierto=unCGP.estaDisponible(2,horaX);	//hay jubilacion
		assertEquals(true,abierto);
	}
	
	@Test
	public void testHorarioParadas() {
		boolean disponible = parada.estaDisponible();
		assertEquals(true, disponible);
	}

	@Test
	public void testHorarioLocal() {
		horaX=new LocalTime(19,00);
		boolean abierto=carrousel.getHorarios().estaDisponible(3, horaX);
		assertEquals(true,abierto);
		horaX=new LocalTime(11,00);
		boolean domingo=carrousel.getHorarios().estaDisponible(1, horaX);
		assertEquals(false,domingo);
		horaX=new LocalTime(15,00);
		boolean abierto2=carrousel.getHorarios().estaDisponible(5,horaX);
		assertEquals(false,abierto2);
	}
}
