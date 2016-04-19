package tipos;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import principal.POI;

public class Banco extends POI {
	int LUNES=1,VIERNES=5;
	private String nombre;
	
	//------------------------DISPONIBILIDAD------------------
	List<Servicio> servicios;
	LocalTime INICIO=new LocalTime(10,00),
			  FIN= new LocalTime(15,00);
	
	public Banco(){
		servicios=new ArrayList<Servicio>();
	}
	

	public void agregarServicio(Servicio unServicio){
		this.servicios.add(unServicio);
	}
	
	
	public boolean estaDisponible(int dia, String hora){
		DateTimeFormatter formato= DateTimeFormat.forPattern("HH:mm");
		LocalTime horaP=LocalTime.parse(hora,formato);
		return ((dia>=LUNES) && (dia<=VIERNES) && (horaP.isAfter(INICIO))&& (horaP.isBefore(FIN)));
	}
	
	public boolean estaDisponible(int dia, String hora,String servicioBuscado){
		boolean abierto=(servicios.stream()
				.filter(unServicio->(servicioBuscado.equalsIgnoreCase(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->unServicio.getHorarios().estaDisponibleSegunLista(dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	
	//---------------BUSQUEDA-----------------------------------
	
	public boolean tienePalabra(String texto){
		return (this.tienePalabraEnNombre(texto) || this.tienePalabraEnServicio(texto));
		
	}
	
	public boolean tienePalabraEnServicio(String texto){
		return this.getServicios().stream().anyMatch(servicio->(servicio.getNombre().contains(texto)));
	}
	
		
	// -------------------GETTERS,SETTERS-----------------
	public List<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(Servicio servicio) {
		this.servicios.add(servicio);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}