package tipos;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import principal.POI;

public class Banco extends POI {
	int LUNES=1,VIERNES=5;
	List<Servicio> servicios;
	LocalTime INICIO=new LocalTime(10,00),
			  FIN= new LocalTime(15,00);
	
	public Banco(){
		servicios=new ArrayList<Servicio>();
	}
	
	public List<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(Servicio servicio) {
		this.servicios.add(servicio);
	}
	public void agregarServicio(Servicio unServicio){
		this.servicios.add(unServicio);
	}
	
	public boolean estaDisponible(int dia, String hora){
		DateTimeFormatter formato= DateTimeFormat.forPattern("HH:mm");
		LocalTime horaP=LocalTime.parse(hora,formato);
		return ((dia>=LUNES) && (dia<=VIERNES) && (horaP.isAfter(INICIO))&& (horaP.isBefore(FIN)));
	}
	
	public void tienePalabra(String texto){
		 getServicios().stream()
				.filter(servicio->(servicio.getNombre()).contains(texto))
				.forEach(s->System.out.println(s.getNombre()));
	}


/*public void setHorarios(){
LocalTime horarioNuevo= LocalTime(10)
for (int dia=2;dia<=6;dia++){			//Lunes a viernes, 10 a 15 hs
	Horario horarioNuevo= horarioNuevo(dia,"10:00","15:00");	//Metodo de la interfaz
	agregarHorario(horarioNuevo);		//Metodo del padre (POI)
	}
*/
		public boolean estaDisponible(int dia, String hora,String servicioBuscado){
		boolean abierto=(servicios.stream()
				.filter(unServicio->(servicioBuscado.equalsIgnoreCase(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->unServicio.getHorarios().estaDisponibleSegunLista(dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
}