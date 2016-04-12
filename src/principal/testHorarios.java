package principal;

import static org.junit.Assert.*;

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

	@Before
	public void initialize() {
		banco = new Banco();
		Servicio rentas=new Servicio("Rentas");
		Horario horarioRentas=rentas.horarioNuevo(1, "10:00", "22:00");
		rentas.agregarHorario(horarioRentas);
		Servicio jubilacion=new Servicio("jubilacion");
		Horario horarioJubilacion=jubilacion.horarioNuevo(2,"05:00", "09:00");
		jubilacion.agregarHorario(horarioJubilacion);
		banco.agregarServicio(rentas);
		banco.agregarServicio(jubilacion);
		
		unCGP = new CGP();
		unCGP.agregarServicio(rentas);
		unCGP.agregarServicio(jubilacion);
		
		parada = new ParadaColectivo();
		carrousel = new Local();
		for (int dia = 2; dia <= 7; dia++) {		//Horarios Carrousel 
			Horario horario1=carrousel.horarioNuevo(dia, "10:00", "13:00");
			carrousel.agregarHorario(horario1);
			Horario horario2=carrousel.horarioNuevo(dia, "17:00", "20:30");
			carrousel.agregarHorario(horario2);
		}

	}

	@Test
	public void testHorariosBanco() {
		boolean domingo = banco.estaDisponible(1, "12:00"); // Banco un domingo? Ja
		assertEquals(false, domingo);
		boolean lunes = banco.estaDisponible(2, "14:00");
		assertEquals(true, lunes);
		boolean viernesTarde = banco.estaDisponible(5, "16:00");	//demasiado temprano...
		assertEquals(false, viernesTarde);
	}
	@Test
	public void testServiciosBanco(){
		boolean abierto=banco.estaDisponible(1, "12:00", "Rentas");
		assertEquals(true, abierto);
		boolean abiertoRentas=banco.estaDisponible(1, "09:00","rentas");
		assertEquals(false, abiertoRentas);
		boolean abiertoJubilacion=banco.estaDisponible(2, "08:00", "jUBiLaciOn");
		assertEquals(true,abiertoJubilacion);
	}
	@Test
	public void testHorarioCGP(){
		boolean abierto=unCGP.estaDisponible(1, "12:00", "Rentas");		
		assertEquals(true,abierto);
		abierto=unCGP.estaDisponible(4,"06:00");		
		assertEquals(false,abierto);
		abierto=unCGP.estaDisponible(2, "06:00");	//hay jubilacion
		assertEquals(true,abierto);
	}
	
	@Test
	public void testHorarioParadas() {
		boolean disponible = parada.estaDisponible();
		assertEquals(true, disponible);
	}

	@Test
	public void testHorarioLocal() {
		boolean abierto=carrousel.estaDisponible(3, "19:00");
		assertEquals(true,abierto);
		boolean domingo=carrousel.estaDisponible(1, "11:00");
		assertEquals(false,domingo);
		boolean abierto2=carrousel.estaDisponible(5,"15:00");
		assertEquals(false,abierto2);
	}
}
