package tipos;

import java.util.ArrayList;
import java.util.List;

import principal.Maquina;
import principal.POI;

public class CGP extends POI{
	private int comuna;
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
	
	public boolean estaDisponible(int dia, String hora,String servicioBuscado){
		boolean abierto=(servicios.stream()
				.filter(unServicio->(servicioBuscado.equalsIgnoreCase(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->estaDisponible(unServicio.getHorariosAtencion(),dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	
	public boolean estaDisponible(int dia, String hora){
		boolean abierto=(servicios.stream()
				.anyMatch(unServicio->estaDisponible(unServicio.getHorariosAtencion(),dia,hora)));			//Algun servicio disponible en ese horario
		return abierto;
	}
	
	//---------------CERCANIA-----------------------------------

	public boolean estaCerca(Maquina puntoActual) {
		return (comuna == puntoActual.getComuna());
	}

	// -------------------GETTERS,SETTERS-----------------
	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;

	}
	//-------------------HORARIOS-----------------------
	
}
