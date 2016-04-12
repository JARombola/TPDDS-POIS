package principal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tipos.Banco;
import tipos.Local;
import tipos.ParadaColectivo;

public class testHorarios {
	Banco banco;
	ParadaColectivo parada;
	Local carrousel;

	@Before
	public void initialize() {
		banco = new Banco();
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
		System.out.println("1");
		boolean domingo = banco.estaDisponible(1, "12:00"); // Banco un domingo? Ja
		assertEquals(false, domingo);
		boolean lunes = banco.estaDisponible(2, "14:00");
		assertEquals(true, lunes);
		boolean viernesTarde = banco.estaDisponible(5, "16:00");
		assertEquals(false, viernesTarde);
	}

	@Test
	public void testHorarioParadas() {
		boolean disponible = parada.estaDisponible();
		assertEquals(true, disponible);
	}

	@Test
	public void testHorarioLocal() {
		System.out.println("2");
		boolean abierto=carrousel.estaDisponible(3, "19:00");
		assertEquals(true,abierto);
		boolean domingo=carrousel.estaDisponible(1, "11:00");
		assertEquals(false,domingo);
		boolean abierto2=carrousel.estaDisponible(5,"15:00");
		assertEquals(false,abierto2);
	}
}
