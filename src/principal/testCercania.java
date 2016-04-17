package principal;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;
import tipos.Rubro;

public class testCercania {
	Banco banco;
	ParadaColectivo parada;
	Local librosSA;
	CGP cgp;
	Rubro libreria;
	Maquina maquina;
	Comuna comuna;
	Coordenadas coordenada;
	@Before
	public void initialize() {
		comuna=new Comuna();
		coordenada=new Coordenadas();
			coordenada.setLatitud(47);
			coordenada.setLongitud(-120);
			comuna.addPunto(coordenada);
			coordenada.setLatitud(45);
			coordenada.setLongitud(-130);
			comuna.addPunto(coordenada);
			coordenada.setLatitud(50);
			coordenada.setLongitud(-130);
			comuna.addPunto(coordenada);
		banco = new Banco();
			banco.setLatitud(47.6798206);
			banco.setLongitud(-122.3271205);
		parada = new ParadaColectivo();
			parada.setLatitud(47.6757206);
			parada.setLongitud(-122.3271205);
		librosSA = new Local();
			librosSA.setLatitud(47.6777206);
			librosSA.setLongitud(-122.3271205);
			libreria = new Rubro();
			libreria.setRadioCercania(0.3);
			librosSA.setRubro(libreria);
		cgp = new CGP();
			cgp.setComuna(comuna);
		maquina = new Maquina(47.6757206,-122.3271205);

			maquina.setComuna(comuna);
		
		}
	
	@Test
	public void testCercaniaBancos() {
		boolean cerca = banco.estaCerca(maquina);
		Assert.assertEquals(true, cerca);
	}
	
	@Test
	public void testCercaniaLocal() {
		boolean cerca = librosSA.estaCerca(maquina);
		Assert.assertEquals(true,cerca);				
	}
	
	@Test
	public void testCercaniaCGPs() {
		boolean cerca = cgp.estaCerca(maquina);
		Assert.assertEquals(true,cerca);
	}
	
	@Test
	public void testCercaniaParada() {
		boolean cerca = parada.estaCerca(maquina);
		Assert.assertEquals(true,cerca);
	}
}
