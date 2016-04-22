package principal;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import tipos.Servicio;

public class EntesConServicios {
	List<Servicio> servicios;
	public List<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}
	public EntesConServicios(){
		servicios=new ArrayList<Servicio>();
	}
	public boolean estaDisponible(int dia, LocalTime hora, String servicioBuscado){
		boolean abierto=(getServicios().stream()
				.filter(unServicio->(unServicio.getNombre().contains(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->unServicio.estaDisponible(dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	public void agregarServicio(Servicio servicioNuevo){
		servicios.add(servicioNuevo);
	}
	public boolean tienePalabra(String palabra){
		return getServicios().stream().anyMatch(servicio->servicio.tienePalabra(palabra));
	}

}

