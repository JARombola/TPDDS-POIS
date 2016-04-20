package tipos;

import principal.EntesConHorarios;


public class Servicio{
	private EntesConHorarios horarios;
	private String nombre;
	private String descripcion;

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
		return (getNombre().contains(palabra));
	}

}
