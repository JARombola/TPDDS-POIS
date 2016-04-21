package principal;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EntesConHorarios {		//Contiene la Lista con Horarios, determina si estan disponibles o no

	List<Horario> horariosAtencion;

	public EntesConHorarios() {
		horariosAtencion = new ArrayList<Horario>();
	}

	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}


	public void horarioNuevo(int dia, String horaInicio, String horaFin) {
		DateTimeFormatter formato= DateTimeFormat.forPattern("HH:mm");
		LocalTime inicio=LocalTime.parse(horaInicio, formato);
		LocalTime fin=LocalTime.parse(horaFin,formato);
		Horario horarioNuevo = new Horario();
		horarioNuevo.setDia(dia);
		horarioNuevo.setInicio(inicio);
		horarioNuevo.setFin(fin);
		horariosAtencion.add(horarioNuevo);
	}

	// -------------------CALCULO DISPONIBILIDAD-----------------

	public boolean estaDisponible() {
		return (estaDisponibleSegunLista()); // Usa el metodo de POI (SUPERCLASE EnteHorario) fecha=HOY
	}
	public boolean estaDisponible(int dia, String hora) {
		return (estaDisponibleSegunLista(dia, hora)); // Usa el metodo de POI, pero con la fecha y hora determinadas
	}

	public boolean estaDisponibleSegunLista() {
		DateTime fechaHoy= DateTime.now(); // HOY
		int nro_dia=fechaHoy.getDayOfWeek();
		String hora=fechaHoy.toLocalTime().toString();
		return (estaDisponibleSegunLista(nro_dia, hora));
	}

	public boolean estaDisponibleSegunLista(int dia, String hora) {
		boolean abierto = (getHorariosAtencion().stream()
				.filter(horario -> (horario.getDia() == dia)) 
				.anyMatch(horario -> horario.estaAbierto(hora))); 
		return abierto;
	}
}
