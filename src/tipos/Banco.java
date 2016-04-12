package tipos;

import java.util.ArrayList;
import java.util.List;

import principal.Horario;
import principal.POI;

public class Banco extends POI {
	List<Servicio> servicios;
	
	public List<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(Servicio servicio) {
		this.servicios.add(servicio);
	}
	public Banco(){
		setHorarios();
		servicios=new ArrayList<Servicio>();
	}
	
	
	public void setHorarios(){
		for (int dia=2;dia<=6;dia++){			//Lunes a viernes, 10 a 15 hs
			Horario horarioNuevo= horarioNuevo(dia,"10:00","15:00");	//Metodo de la interfaz
			agregarHorario(horarioNuevo);		//Metodo del padre (POI)
			}
	}
		
		
	}

