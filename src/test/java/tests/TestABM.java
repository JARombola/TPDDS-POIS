package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import terminales.Mapa;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;

public class TestABM {
	Banco poi1;
	CGP poi2;
	Banco poi3;
	Local poi4;
	Mapa mapa;
	
	@Before
	public void initialize() {
		poi1 = new Banco();
			poi1.setId(12);
			poi1.setNombre("La Nacion");
		
		poi2 = new CGP();
			poi2.setId(4);
			poi2.setNombre("CGP Quien Sabe");
			
		poi3 = new Banco();
			poi3.setId(12);
			poi3.setNombre("Ciudad");
		
		poi4 = new Local();
			poi4.setId(8);
			
		mapa = new Mapa();
			mapa.setPOI(poi1);
			mapa.setPOI(poi2);
		
	}
	
	@Test
	public void testModificarPoi(){
		mapa.agregarOmodificar(poi3);
		Assert.assertEquals(poi1.getNombre(),"Ciudad");
	}
	
	@Test
	public void testCrearPoi(){
		mapa.agregarOmodificar(poi4);
		Assert.assertEquals(3,mapa.getListaPOIS().size());
		
	}
	
	@Test		
	public void testEliminarUnPOI() throws Exception{
		mapa.eliminarPOI(poi1.getId());
		Assert.assertEquals(mapa.getListaPOIS().size(), 1);
	}
	
	@Test(expected=Exception.class)		
	public void testEliminarPOIinexistente() throws Exception{
		mapa.eliminarPOI(poi3.getId());		//lo elimina bien
		mapa.eliminarPOI(poi3.getId());		//falla!
	}
	
}
