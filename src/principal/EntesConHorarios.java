package principal;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public abstract class EntesConHorarios {

	List<Horario> horariosAtencion;

	public EntesConHorarios() {
		horariosAtencion = new ArrayList<Horario>();
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

	// -------------------CALCULO DISPONIBILIDAD-----------------

	public boolean estaDisponible() {
		return (estaDisponibleSegunLista(this.getHorariosAtencion())); // Usa el metodo de POI (SUPERCLASE EnteHorario) fecha=HOY
	}
	public boolean estaDisponible(int dia, LocalTime hora) {
		return (estaDisponibleSegunLista(this.getHorariosAtencion(), dia, hora)); // Usa el metodo de POI, pero con la fecha y hora determinadas
	}

	public boolean estaDisponibleSegunLista(List<Horario> horariosAtencion) {
		DateTime fechaHoy= DateTime.now(); // HOY
		int nro_dia=fechaHoy.getDayOfWeek();
		LocalTime hora=fechaHoy.toLocalTime();
		return (estaDisponibleSegunLista(horariosAtencion, nro_dia, hora));
	}

	public boolean estaDisponibleSegunLista(List<Horario> horariosAtencion, int dia, LocalTime hora) {
		boolean abierto = (horariosAtencion.stream()
				.filter(horario -> (horario.getDia() == dia)) 
				.anyMatch(horario -> horario.estaAbierto(hora))); 
		return abierto;
	}
}
