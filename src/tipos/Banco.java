package tipos;

import java.util.ArrayList;
import java.util.List;

import principal.Horario;
import principal.POI;

public class Banco extends POI {
	List<Servicio> servicios;
	
	public Banco(){
		setHorarios();
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
	
	
	public void setHorarios(){
		for (int dia=2;dia<=6;dia++){			//Lunes a viernes, 10 a 15 hs
			Horario horarioNuevo= horarioNuevo(dia,"10:00","15:00");	//Metodo de la interfaz
			agregarHorario(horarioNuevo);		//Metodo del padre (POI)
			}
	}
	public boolean estaDisponible(int dia, String hora,String servicioBuscado){
		boolean abierto=(servicios.stream()
				.filter(unServicio->(servicioBuscado.equalsIgnoreCase(servicioBuscado)))		//Filtra los dias que coinciden con la fecha
				.anyMatch(unServicio->estaDisponibleSegunLista(unServicio.getHorariosAtencion(),dia,hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	/*public boolean estaDisponible(int dia, String hora){
		if (dia>=2 && dia<=6){
			int resultado=hora.compareTo("10:00");		// cuanto hace que esta abierto (si es negativo es xq esta cerrado :P)
			if (resultado>=0) {								//la cuenta es: momento-min
				resultado=hora.compareTo("15:00");		// cuanto hace que cerró (si es negativo es xq todavia está abierto :P)
				if (resultado<=0){							//momento-max
					return true;
				}
			}	
		}
		return false;
	}*/
}