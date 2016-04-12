package principal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public interface Horarios {
	
	public default Horario horarioNuevo(int dia, String horaInicio, String horaFin){
		Horario horarioNuevo=new Horario();
		horarioNuevo.setDia(dia);
		horarioNuevo.setInicio(horaInicio);
		horarioNuevo.setFin(horaFin);
		return (horarioNuevo);						//Devuelve un HORARIO
	}
	
	public default boolean estaDisponible(List<Horario> horariosAtencion){
		Calendar diaActual=new GregorianCalendar();		//HOY
		int hoy=diaActual.get(Calendar.DAY_OF_WEEK);
		int horaActual = diaActual.get(Calendar.HOUR_OF_DAY);
		String hora;
		if (horaActual<10) {hora="0"+horaActual;}
			else {hora=Integer.toString(horaActual);};
		int minutos=(diaActual.get(Calendar.MINUTE));
		if (minutos<10) {hora+=":0"+minutos;}
		else {hora+=":"+minutos;};				
		//TODO ESO PARA CONSEGUIR EL DIA Y LA HORA DEL SIST -.-
		return(estaDisponible(horariosAtencion,hoy,hora));
	}
	
	public default boolean estaDisponible(List<Horario> horariosAtencion,int dia, String hora){	//Fecha minima y maxima (Intervalo en que esta disponible el lugar).					// Si uso "hora" el lambda tira error (?) -.-
		boolean abierto=(horariosAtencion.stream()
						.filter(horario->(horario.getDia()==dia))		//Filtra los dias que coinciden con la fecha
						.anyMatch(horario->horario.estaAbierto(hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	
}
