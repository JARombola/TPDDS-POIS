package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import principal.Direccion;
import principal.Mapa;
import tipos.Banco;
import tipos.CGP;

public class testABM {
	CGP cgp;
	Banco banco;
	Mapa mapa;
	Direccion dire;
	@Before
	public void initialize(){
		mapa = new Mapa();
		cgp = new CGP();
		dire=new Direccion();
		banco = new Banco();
		dire.setCalle("hola");
		dire.setNumero(100);
		cgp.setDireccion(dire);
		
	}
	
	@Test		
	public void agregarPOI(){
		mapa.agregarOmodificar(banco);
		mapa.agregarOmodificar(cgp);
		Assert.assertEquals(mapa.getListaPOIS().size(), 2);

	}
	@Test		
	public void modificarPOI(){
		mapa.agregarOmodificar(banco);
		mapa.agregarOmodificar(cgp);
		dire.setNumero(150);
		dire.setCalle("chau");
		cgp.setDireccion(dire);
		mapa.agregarOmodificar(cgp);
		int pos=mapa.getListaPOIS().indexOf(cgp);
	     System.out.println(mapa.getListaPOIS().get(pos).getDireccion().getCalle());
	     System.out.println(mapa.getListaPOIS().get(pos).getDireccion().getNumero());

	}
	@Test		
	public void eliminarPOI(){
		mapa.agregarOmodificar(banco);
		mapa.agregarOmodificar(cgp);
		mapa.eliminarPOI(cgp);
		Assert.assertEquals(mapa.getListaPOIS().size(), 1);
	}
}
