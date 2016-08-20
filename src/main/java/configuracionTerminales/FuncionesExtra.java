package configuracionTerminales;

import java.util.HashMap;
import java.util.Map;

import principal.Terminales.Busqueda;
import principal.Terminales.Terminal;

public class FuncionesExtra {
	
	private int tiempoMax;
	private Map<String,Boolean> opciones;		//Usa el map para activar/desactivar las opciones... :)
	private Terminal terminal;

	public FuncionesExtra(int tiempoMax){
		this.tiempoMax=tiempoMax;
		opciones=new HashMap<String,Boolean>();
		opciones.put("HISTORIAL", false);
		opciones.put("MAIL", false);
	}
	
	public void inicioBusqueda(){
		TiempoEjecucion.Start();
	}
	
	public void finBusqueda(Busqueda datosBusqueda){
		TiempoEjecucion.Stop();
		double tiempoBusqueda= TiempoEjecucion.getTiempoEjecucion();
		datosBusqueda.setTiempoBusqueda(tiempoBusqueda);
		this.enviarMail(tiempoBusqueda);
		this.guardarBusqueda(datosBusqueda);
	}
	
	private void guardarBusqueda(Busqueda datosBusqueda) {
		if (getOpciones().get("HISTORIAL")){
			terminal.guardarBusquedas(datosBusqueda);
		}
		
	}

	private void enviarMail(double tiempoBusqueda){
		if (getOpciones().get("MAIL") && tiempoBusqueda>tiempoMax){		//activado el mail, y el tiempo se excedió
			EnviadorMails mail=new EnviadorMails();
			mail.enviarMail();
		}
	}

	public Map<String, Boolean> getOpciones() {
			return opciones;
	}	
	
	public void activarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), true);
	}
	
	public void desactivarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), false);
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}
