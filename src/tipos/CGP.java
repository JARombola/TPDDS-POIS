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
	//------------------------HORARIOS------------------
	public List<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(Servicio servicio) {
		this.servicios.add(servicio);
	}
	
	public void agregarServicio(Servicio unServicio){
		this.servicios.add(unServicio);
	}
	
	public boolean estaDisponible(int dia, LocalTime hora,String servicioBuscado){
		boolean abierto=(servicios.stream()
				.filter(unServicio->(servicioBuscado.equalsIgnoreCase(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->estaDisponibleSegunLista(unServicio.getHorariosAtencion(),dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	
	public boolean estaDisponible(int dia, LocalTime hora){
		boolean abierto=(servicios.stream()
				.anyMatch(unServicio->estaDisponibleSegunLista(unServicio.getHorariosAtencion(),dia,hora)));			//Algun servicio disponible en ese horario
		return abierto;
	}
	
	//---------------CERCANIA-----------------------------------

	public boolean estaCerca(Maquina puntoActual) {
		return (puntoActual.getComuna().dentroDeLaZona(puntoActual.getCoordenadas()));
	}

	// -------------------GETTERS,SETTERS-----------------
	public Comuna getComuna() {
		return comuna;
	}
	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}	
	public void tienePalabra(String texto){
		 getServicios().stream()
				.filter(servicio->(servicio.getNombre()).contains(texto))
				.forEach(s->System.out.println(s.getNombre()));
	}
}
