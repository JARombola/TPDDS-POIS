package tipos;

import java.util.ArrayList;
import java.util.List;

import principal.Horario;
import principal.Horarios;

public class Servicio implements Horarios {
	public String nombre;
	public String descripcion;
	List<Horario> horariosAtencion;

	public Servicio(String nombre) {
		setNombre(nombre);
		horariosAtencion = new ArrayList<Horario>();
	}
	
	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}

	public void setHorariosAtencion(Horario horario) {
		this.horariosAtencion.add(horario);
	}

	public void agregarHorario(Horario horarioNuevo) {
		horariosAtencion.add(horarioNuevo);
	}



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
