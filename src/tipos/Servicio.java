package tipos;

import java.util.List;

import org.joda.time.LocalTime;

import principal.EntesConHorarios;


public class Servicio{
	private EntesConHorarios horarios;
	private String nombre;
	private String descripcion;
	private List<String> tags;
	
	public List<String> getTags() {
		return tags;
	}

	public void agregarTag(String tag) {
		this.tags.add(tag);
	}

	public Servicio(String nombre) {
		horarios = new EntesConHorarios();
		this.setNombre(nombre);
	}

	// -------------------GETTERS,SETTERS-----------------
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public EntesConHorarios getHorarios() {
		return horarios;
	}
	public boolean tienePalabra(String palabra){
		return (getNombre().contains(palabra));// || getTags().contains(palabra));
	}
	
	public boolean estaDisponible(int dia,LocalTime hora){
		return getHorarios().estaDisponible(dia,hora);
	}

}
