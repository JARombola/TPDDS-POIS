package pois;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.LocalTime;


public class ListaHorarios {		//Contiene la Lista con Horarios, determina si estan disponibles o no
	@Id @GeneratedValue
	private int id;
	
	private List<Horario> horariosAtencion;

	public ListaHorarios() {
		
	}
	
	public boolean estaDisponible(int dia, LocalTime hora) {
		return (estaDisponibleSegunLista(dia, hora));
	}

	public boolean estaDisponibleSegunLista(int dia, LocalTime hora) {
		boolean abierto = getHorariosAtencion().stream()
							.filter(horario -> (horario.getDia() == dia)) 
							.anyMatch(horario -> horario.estaAbierto(hora)); 
		return abierto;
	}
	
	//--------------------------GETTERS/SETTERS/BLA----------------------------
	
	public void setHorariosAtencion(List<Horario> horariosAtencion) {
		this.horariosAtencion = horariosAtencion;
	}

	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}

	public void horarioNuevo(int dia, LocalTime horaInicio, LocalTime horaFin) {
			Horario horarioNuevo = new Horario();
			horarioNuevo.setDia(dia);
			horarioNuevo.setInicio(horaInicio);
			horarioNuevo.setFin(horaFin);
			horariosAtencion.add(horarioNuevo);
	}
		
	public void agregarHorario(Horario horario){
		horariosAtencion.add(horario);
	}	

}
