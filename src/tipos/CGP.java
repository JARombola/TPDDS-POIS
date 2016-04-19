package tipos;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.uqbar.geodds.Point;

import principal.Comuna;
import principal.Coordenadas;
import principal.Maquina;
import principal.POI;

public class CGP extends POI{
	private Comuna comuna;
	List<Servicio> servicios;

	public CGP() {
		servicios=new ArrayList<Servicio>();
	}
	//------------------------DISPONIBILIDAD------------------
	
	public void agregarServicio(Servicio unServicio){
		this.servicios.add(unServicio);
	}
	
	public boolean estaDisponible(int dia, String hora,String servicioBuscado){
		boolean abierto=(servicios.stream()
				.filter(unServicio->(servicioBuscado.equalsIgnoreCase(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->unServicio.getHorarios().estaDisponibleSegunLista(dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	
	public boolean estaDisponible(int dia, String hora){
		boolean abierto=(servicios.stream()
				.anyMatch(unServicio->unServicio.getHorarios().estaDisponibleSegunLista(dia,hora)));			//EL servicio le pregunta a sus horarios si esta disponible
		return abierto;
	}
	
	//---------------CERCANIA-----------------------------------

	public boolean estaCerca(Maquina puntoActual) {
		return (puntoActual.getComuna().dentroDeLaZona(puntoActual.getCoordenadas()));
		
	}
	
	//---------------BUSQUEDA-----------------------------------
	
	public boolean tienePalabra(String texto){
		return (this.tienePalabraEnNombre(texto) || this.tienePalabraEnServicio(texto));
		
	}
	
	public boolean tienePalabraEnServicio(String texto){
		return this.getServicios().stream().anyMatch(servicio->(servicio.getNombre().contains(texto)));
	}

	// -------------------GETTERS,SETTERS-----------------
	public Comuna getComuna() {
		return comuna;
	}
	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}	
	
	public List<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(Servicio servicio) {
		this.servicios.add(servicio);
	}
	
	
}
