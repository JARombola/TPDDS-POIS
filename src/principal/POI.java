package principal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class POI {
	private String nombre;
	private String direccion;
	private double longiud;
	private double latitud;
	List<Horario> horariosAtencion;

	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}

	public void agregarHorario(Horario horarioNuevo) {	//Agrega un HORARIO a la lista de horarios
		this.horariosAtencion.add(horarioNuevo);
	}

	// -------------------GETTERS,SETTERS-----------------
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getLongiud() {
		return longiud;
	}

	public void setLongiud(double longiud) {
		this.longiud = longiud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	// -----------------------METODOS----------------------------------
	public POI() {
		horariosAtencion=new ArrayList<Horario>();
	}
	
	public boolean estaDisponible(int dia, String hora){	//Fecha minima y maxima (Intervalo en que esta disponible el lugar).					// Si uso "hora" el lambda tira error (?) -.-
		boolean abierto=(horariosAtencion.stream()
						.filter(horario->(horario.getDia()==dia))
						.anyMatch(horario->horario.estaAbierto(hora)));
		return abierto;
	}
	
	public boolean estaDisponible(){
		Calendar diaActual=new GregorianCalendar();		//HOY
		int hoy=diaActual.get(Calendar.DAY_OF_WEEK);
		String hora = Integer.toString(diaActual.get(Calendar.HOUR_OF_DAY));
		int minutos=(diaActual.get(Calendar.MINUTE));
		if (minutos<10) {hora+=":0"+minutos;}
		else {hora+=":"+minutos;};
		return(estaDisponible(hoy,hora));
	}
}
