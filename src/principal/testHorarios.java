package principal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tipos.Banco;

public class testHorarios {
	Banco banco;
	@Before
	public void initialize() {
		banco = new Banco();
		banco.setHorarios();
		
	}

	@Test
	public void testHorariosBanco() {
		boolean domingo = banco.estaDisponible(1, "12:00");	//Banco un domingo? Ja
		assertEquals(false, domingo);
		boolean lunes=banco.estaDisponible(2,"14:00");
		assertEquals(true,lunes);
		boolean viernesTarde=banco.estaDisponible(5,"16:00");
		assertEquals(false,viernesTarde);
	}
}
