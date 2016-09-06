package pois;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import tiposPoi.Servicio;

public class ListaServicios {
	List<Servicio> servicios;
	
	public List<Servicio> getServicios() {
		return servicios;
	}
	
	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}
	
	public ListaServicios(){
		
	}
	
	public boolean estaDisponible(int dia, LocalTime hora, String servicioBuscado){
		boolean abierto=(getServicios().stream()
				.filter(unServicio->(unServicio.tienePalabra(servicioBuscado)))		
				.anyMatch(unServicio->unServicio.estaDisponible(dia,hora)));		
		return abierto;
	}
	
	public void agregarServicio(Servicio servicioNuevo){
		servicios.add(servicioNuevo);
	}
	
	public boolean tienePalabra(String palabra){
		return getServicios().stream().anyMatch(servicio->servicio.tienePalabra(palabra));
	}

}

