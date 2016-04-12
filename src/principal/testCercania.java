package principal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tipos.Banco;
import tipos.CGP;
//import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Rubro;

public class testCercania {
	Banco banco;
	ParadaColectivo parada;
	//Local librosSA;
	CGP cgp;
	Rubro libreria;
	Maquina maquina;

	@Before
	public void initialize() {
		banco = new Banco();
			banco.setLatitud(47.6798206);
			banco.setLongitud(-122.3271205);
		parada = new ParadaColectivo();
		//librosSA = new Local();
		//	libreria = new Rubro();
		//	libreria.setRadioCercania(0.3);
		//	librosSA.setRubro(libreria);
		cgp = new CGP();
			cgp.setComuna(4);
		maquina = new Maquina(47.6788206, -122.3271205);
			maquina.setComuna(4);
		
		}

	@Test
	public void testCercaniaBancos() {
		boolean cerca = banco.estaCerca(maquina);
		assertEquals(false,cerca);				//ESTA EN OTRO PLANETA CASI
	}
	
	@Test
	public void testCercaniaCGPs() {
		boolean cerca = cgp.estaCerca(maquina);
		assertEquals(true,cerca);
	}
	


}
