package principal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class EntesConHorarios {

	List<Horario> horariosAtencion;

	public EntesConHorarios() {
		horariosAtencion = new ArrayList<Horario>();
	}

	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}

	public void agregarHorario(Horario horarioNuevo) { // Agrega un HORARIO a la
														// lista de horarios
		this.horariosAtencion.add(horarioNuevo);
	}

	public Horario horarioNuevo(int dia, String horaInicio, String horaFin) {
		Horario horarioNuevo = new Horario();
		horarioNuevo.setDia(dia);
		horarioNuevo.setInicio(horaInicio);
		horarioNuevo.setFin(horaFin);
		return (horarioNuevo); // Devuelve un HORARIO
	}

	// -------------------CALCULO DISPONIBILIDAD-----------------

	public boolean estaDisponible() {
		return (estaDisponibleSegunLista(this.getHorariosAtencion())); // Usa el
																		// metodo
																		// de
																		// POI
																		// (interfaz
																		// Horario),
																		// fecha=HOY
	}

	public boolean estaDisponible(int dia, String hora) {
		return (estaDisponibleSegunLista(this.getHorariosAtencion(), dia, hora)); // Usa
																					// el
																					// metodo
																					// de
																					// POI,
																					// pero
																					// con
																					// la
																					// fecha
																					// y
																					// hora
																					// determinadas
	}

	public boolean estaDisponibleSegunLista(List<Horario> horariosAtencion) {
		Calendar diaActual = new GregorianCalendar(); // HOY
		int hoy = diaActual.get(Calendar.DAY_OF_WEEK);
		int horaActual = diaActual.get(Calendar.HOUR_OF_DAY);
		String hora;
		if (horaActual < 10) {
			hora = "0" + horaActual;
		} else {
			hora = Integer.toString(horaActual);
		}
		;
		int minutos = (diaActual.get(Calendar.MINUTE));
		if (minutos < 10) {
			hora += ":0" + minutos;
		} else {
			hora += ":" + minutos;
		}
		;
		// TODO ESO PARA CONSEGUIR EL DIA Y LA HORA DEL SIST -.-
		return (estaDisponibleSegunLista(horariosAtencion, hoy, hora));
	}

	public boolean estaDisponibleSegunLista(List<Horario> horariosAtencion, int dia, String hora) { // Fecha
																									// minima
																									// y
																									// maxima
																									// (Intervalo
																									// en
																									// que
																									// esta
																									// disponible
																									// el
																									// lugar).
																									// //
																									// Si
																									// uso
																									// "hora"
																									// el
																									// lambda
																									// tira
																									// error
																									// (?)
																									// -.-
		boolean abierto = (horariosAtencion.stream().filter(horario -> (horario.getDia() == dia)) // Filtra
																									// los
																									// dias
																									// que
																									// coinciden
																									// con
																									// la
																									// fecha
				.anyMatch(horario -> horario.estaAbierto(hora))); // se fija si
																	// el
																	// horario
																	// coincide
																	// con los
																	// registrados
		return abierto;
	}
}
