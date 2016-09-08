package tiposPoi;


import org.joda.time.LocalTime;

import pois.Comuna;
import pois.ListaServicios;
import pois.POI;
import terminales.Maquina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CGP extends POI{
	@Id @GeneratedValue
	private int id;
	private Comuna comuna;
	private ListaServicios servicios;

	public CGP() {

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
	
	public ListaServicios getServicios() {
		return servicios;
	}

	public void setServicios(ListaServicios servicios) {
		this.servicios = servicios;
	}
	
}
