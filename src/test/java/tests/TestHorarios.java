
package tests;


import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pois.ListaHorarios;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;
import tiposPoi.Servicio;

public class TestHorarios {
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
		ListaHorarios horarios3=new ListaHorarios();
		carrousel = new Local();
		carrousel.setHorarios(horarios3);
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
		boolean domingo = banco.estaDisponible(7, horaX,""); // Banco un domingo? Ja
		Assert.assertEquals(false, domingo);
		horaX=new LocalTime(14,00);
		boolean lunes = banco.estaDisponible(2, horaX,"");
		Assert.assertEquals(true, lunes);
		horaX= new LocalTime (16,00);
		boolean viernesTarde = banco.estaDisponible(5, horaX,"");	//demasiado temprano...
		Assert.assertEquals(false, viernesTarde);
	}
	
	@Test
	public void testServiciosBanco(){
		horaX=new LocalTime(12,00);
		boolean abierto=banco.estaDisponible(1, horaX, "Rentas");
		Assert.assertEquals(true, abierto);
		horaX=new LocalTime(9,00);
		boolean abiertoRentas=banco.estaDisponible(1,horaX,"rentas");
		Assert.assertEquals(false, abiertoRentas);
		horaX=new LocalTime(8,00);
		boolean abiertoJubilacion=banco.estaDisponible(2, horaX, "jubilacion");
		Assert.assertEquals(true,abiertoJubilacion);
	}
	
	@Test
	public void testHorarioCGP(){
		horaX=new LocalTime(12,00);
		boolean abierto=unCGP.estaDisponible(1, horaX, "Rentas");		
		Assert.assertEquals(true,abierto);
		horaX = new LocalTime(6,00);
		abierto=unCGP.estaDisponible(4, horaX,"");		
		Assert.assertEquals(false,abierto);
		horaX=new LocalTime(6,00);
		abierto=unCGP.estaDisponible(2,horaX,"");	//hay jubilacion
		Assert.assertEquals(true,abierto);
	}
	
	@Test
	public void testHorarioParadas() {
		horaX=new LocalTime(6,00);
		boolean disponible = parada.estaDisponible(3,horaX,"");
		Assert.assertEquals(true, disponible);
	}

	@Test
	public void testHorarioLocal() {
		horaX=new LocalTime(19,00);
		boolean abierto=carrousel.estaDisponible(3, horaX,"");
		Assert.assertEquals(true,abierto);
		horaX=new LocalTime(11,00);
		boolean domingo=carrousel.estaDisponible(1, horaX,"");
		Assert.assertEquals(false,domingo);
		horaX=new LocalTime(15,00);
		boolean abierto2=carrousel.estaDisponible(5,horaX,"");
		Assert.assertEquals(false,abierto2);
	}
}
