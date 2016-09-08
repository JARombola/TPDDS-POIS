package pois;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.joda.time.LocalTime;

import tiposPoi.Servicio;

public class ListaServicios {
	@Id @GeneratedValue
	private int id;
	@OneToMany
	private List<Servicio> servicios;
	
	public ListaServicios(){	
	}
	
	public List<Servicio> getServicios() {
		return servicios;
	}
	
	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

