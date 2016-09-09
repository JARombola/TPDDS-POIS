package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pois.Comuna;
import pois.Coordenadas;
import terminales.Maquina;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;
import tiposPoi.Rubro;

public class TestCercania {
	Banco banco;
	ParadaColectivo parada;
	Local librosSA;
	Local kioskoPepe;
	CGP cgp;
	Rubro libreria;
	Rubro kioskoDeDiarios;
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
			libreria = new Rubro("libros");
			libreria.setRadioCercania(0.3);
			librosSA.setRubro(libreria);
			
		kioskoPepe = new Local ();
			kioskoPepe.setLatitud(47.678080);
			kioskoPepe.setLongitud(-122.326835);
			kioskoDeDiarios = new Rubro("kioskito");
			kioskoDeDiarios.setRadioCercania(0.2);
			kioskoPepe.setRubro(kioskoDeDiarios);
			
		cgp = new CGP();
			cgp.setComuna(comuna);
		
			
		maquina = new Maquina(47.6757206,-122.3271205);

			maquina.setComuna(comuna);
		
		}
	
	@Test
	public void testCercaniaBancos() {
		Assert.assertTrue(banco.estaCerca(maquina));
	}
	
	@Test
	public void testCercaniaLocal() {
		Assert.assertTrue(librosSA.estaCerca(maquina));				
	}
	
	@Test
	public void testCercaniaLocalKiosko () {
		Assert.assertFalse(kioskoPepe.estaCerca(maquina));
	}
	
	@Test
	public void testCercaniaCGPs() {
		Assert.assertTrue(cgp.estaCerca(maquina));
	}
	
	@Test
	public void testCercaniaParada() {
		Assert.assertTrue(parada.estaCerca(maquina));
	}
}
