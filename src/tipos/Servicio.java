package tipos;

import principal.EntesConHorarios;


public class Servicio extends EntesConHorarios {
	public String nombre;
	public String descripcion;

	public Servicio(String nombre) {
		setNombre(nombre);
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

}
