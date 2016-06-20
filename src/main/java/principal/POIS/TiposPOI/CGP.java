package principal.POIS.TiposPOI;


import org.joda.time.LocalTime;

import principal.POIS.Comuna;
import principal.POIS.EntesConServicios;
import principal.POIS.POI;
import principal.Terminales.Maquina;

public class CGP extends POI{
	private Comuna comuna;
	private EntesConServicios servicios;

	public CGP() {
		servicios=new EntesConServicios();
	}
	
	public void modificar(Banco poiEntrante){
		if(poiEntrante.getServicios().getServicios().size()>0){
			servicios = poiEntrante.getServicios();
		}
		super.modificar(poiEntrante);
	}
	
	//------------------------DISPONIBILIDAD------------------
	
	public void agregarServicio(Servicio unServicio){
		this.servicios.agregarServicio(unServicio);
	}
	
	public boolean estaDisponible(int dia, LocalTime hora,String servicioBuscado){
	return getServicios().estaDisponible(dia, hora, servicioBuscado);
	}
	/*
	public boolean estaDisponible(int dia, LocalTime hora){
		boolean abierto=estaDisponible(dia,hora,"");	//usa la de arriba
		return abierto;
	}
	*/
	//---------------CERCANIA-----------------------------------

	public boolean estaCerca(Maquina puntoActual) {
		return (puntoActual.getComuna().dentroDeLaZona(puntoActual.getCoordenadas()));
		
	}
	
	//---------------BUSQUEDA-----------------------------------
	
	public boolean tienePalabra(String texto){
		return (super.tienePalabra(texto) || tienePalabraEnServicio(texto));
		
	}
	
	public boolean tienePalabraEnServicio(String texto){
		return getServicios().tienePalabra(texto);
	}

	// -------------------GETTERS,SETTERS-----------------
	public Comuna getComuna() {
		return comuna;
	}
	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}	
	
	public EntesConServicios getServicios() {
		return servicios;
	}
}